package repository;

import entity.Spot;
import entity.Ticket;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.joda.time.DateTime;
import utils.NewCarAction;
import utils.TicketExpirationAction;

import static javax.ejb.LockType.READ;
import static javax.ejb.LockType.WRITE;


@Singleton
@ConcurrencyManagement(value = ConcurrencyManagementType.CONTAINER)
@Local(Repository.class)
@AccessTimeout(value=30000)
@Named(value = "repositoryimpl")
public class RepositoryImpl implements Repository {
    private final static Logger LOGGER = Logger.getLogger(RepositoryImpl.class.toString());
    private final static Integer SPOT_WITH_NO_TICKET_EXPIRATION = 30; // TODO seconds!

    private EntityManagerFactory emfSpot;
    private EntityManager emSpot;

    private Ticket shortestTicket;
    private SortedSet<Ticket> ticketSortedSet;
    private Timer ticketTimer;

    @Resource
    TimerService timerService;

    /*----------------MESSAGE DRIVEN BEAN PART-----------------------------*/
    @Resource(mappedName = "java:/project/RepositoryToNotifierQueue")
    javax.jms.Queue queue;
    @Inject
    JMSContext jmsContext;
    /*----------------MESSAGE DRIVEN BEAN PART-----------------------------*/


    @PostConstruct
    public void init() {
        ticketSortedSet = new TreeSet<>(((o1, o2) -> o1.getEnd().compareTo(o2.getEnd())));
        emfSpot = Persistence.createEntityManagerFactory("hibernate-spot-repository");
        emSpot = emfSpot.createEntityManager();
        LOGGER.info(() -> "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX repository.Repository initialization completed");
    }


    public RepositoryImpl() {
    }

    @Override
    @Lock(WRITE)
    public void addSpot(Spot spot) {
        LOGGER.info(() -> "Add spot:" + spot);
        emSpot.getTransaction().begin();
        if (!emSpot.contains(spot)) {
            emSpot.persist(spot);
            emSpot.flush();
        }
        emSpot.getTransaction().commit();
        LOGGER.info(() -> "Added spot to database: " + spot);
        LOGGER.info("Scheduled for ticket lookup for spot number " + spot.getPlace() + " in " + SPOT_WITH_NO_TICKET_EXPIRATION + "seconds");
        triggerNewCarAction(spot.getPlace());
    }

    public void triggerNewCarAction(Integer place) {
        long intervalDuration = SPOT_WITH_NO_TICKET_EXPIRATION*1000;
        LOGGER.info("Setting a programmatic timeout for "
                + intervalDuration + " milliseconds from now. NewCarAction scheduled for place " + place);
        NewCarAction newCarAction = new NewCarAction(place);
        timerService.createTimer(intervalDuration, newCarAction);
    }

    public void triggerTicketExpirationAction(Ticket ticket) {
        TicketExpirationAction ticketExpirationAction = new TicketExpirationAction(ticket.getPlace());
        ticketTimer = timerService.createTimer(ticket.getEnd().toDate(),ticketExpirationAction);
        LOGGER.info("Setting a programmatic timeout to "
                + ticket.getEnd() + ". TicketExpirationAction scheduled for ticket place " + ticketExpirationAction.getPlace());
        Ticket first = ticketSortedSet.first();
        LOGGER.info("first ticket: " + first);
        assert first == shortestTicket;
        LOGGER.info("assert passed");
        ticketSortedSet.remove(first);
        LOGGER.info("first ticket removed");

        if (ticketSortedSet.isEmpty()) {
            shortestTicket = null;
        }
        else {
            shortestTicket = ticketSortedSet.first();
        }

        LOGGER.info("new shortest ticket initialized: " + shortestTicket);
    };


    @Timeout
    public void programmaticTimeout(Timer timer) {
        Serializable info = timer.getInfo();
        if (info instanceof NewCarAction) {
            NewCarAction newCarAction = (NewCarAction)info;
            Integer place = newCarAction.getPlace();
            LOGGER.info("Programmatic timeout occurred. Looking up to ticket for place " + place + " in database");
            Ticket ticket = findTicketByPlace(place);
            if (ticket == null) {
                LOGGER.info("No ticket found for place " + place + ". Event detector is being informed!");
                //TODO send JMS message here: new car from :place = place didn't pay for spot
                sendMessage("TICKET NOT BOUGHT: PLACE " + place);
            }
            else {
                LOGGER.info("Found ticket for place " + place + ". Event detector won't be informed!. Ticket: " + ticket);
            }
        }

        if (info instanceof TicketExpirationAction) {
            LOGGER.info("Ticket expiration action");
            TicketExpirationAction ticketExpirationAction = (TicketExpirationAction)info;
            LOGGER.info("Checking whether spot place " + ticketExpirationAction.getPlace() + "....");
            Spot spot = findSpotByPlace(ticketExpirationAction.getPlace());
            if (spot == null) {
                LOGGER.info("Spot is not occupied.");
            }
            else {
                LOGGER.info("Spot is occupied and ticket is expired. Inform event detector!");
                LOGGER.info("Event detector is being informed.....");
                // TODO send JMS message here: ticket expired and car from :place = place is still occupying spot
                sendMessage("TICKET EXPIRATION: place " + ticketExpirationAction.getPlace());
            }
        }
    }

    @Override
    @Lock(WRITE)
    public void removeSpot(Integer place) {
        Session session = emSpot.unwrap(Session.class);
        String hql = "delete from Spot where place = :place";
        Transaction tx = null;
        tx = session.beginTransaction();
        Query query = session.createQuery(hql).setParameter("place", place);
        query.executeUpdate();
        tx.commit();
        LOGGER.info(() -> "Spot " + place + "deleted from database");
    }

    @Override
    @Lock(WRITE)
    public void addTicket(Ticket ticket) {
        emSpot.getTransaction().begin();
        if (!emSpot.contains(ticket)) {
            emSpot.persist(ticket);
            emSpot.flush();
        }
        emSpot.getTransaction().commit();
        LOGGER.info(() -> "Added ticket to database: " + ticket);

        shortestTicketProcedure(ticket);
    }

    @Override
    @Lock(WRITE)
    public List<Spot> getAllSpots() {
        String hql = "from Spot";
        javax.persistence.Query query = emSpot.createQuery(hql);
        return query.getResultList();
    }

    @Override
    @Lock(WRITE)
    public List<Ticket> getAllTickets() {
        String hql = "from Ticket ";
        javax.persistence.Query query = emSpot.createQuery(hql);
        return query.getResultList();
    }

    @Override
    @Lock(WRITE)
    public List<Ticket> getValidTickets() {
        return getValidTicketsWithExpirationBoundary(0);
    }

    @Override
    @Lock(WRITE)
    public List<Ticket> getValidTicketsWithExpirationBoundary(final Integer EXPIRATION) {
        List<Ticket> tickets = getAllTickets();
        List<Ticket> validTickets = new ArrayList<>(tickets.size());
        validTickets.addAll(tickets.stream().filter(ticket -> ticket.getEnd().plusMinutes(EXPIRATION).isAfterNow()).collect(Collectors.toList()));
        return validTickets;
    }

    @Override
    @Lock(WRITE)
    public Ticket findTicketByPlace(Integer place) {
        String hql = "from Ticket where place = :place";
        javax.persistence.Query query = emSpot.createQuery(hql).setParameter("place",place);
        List results = query.getResultList();
        Ticket ticket = null;
        if (!results.isEmpty()) {
            ticket = (Ticket)results.get(0);
        }
        return ticket;
    }

    @Override
    @Lock(WRITE)
    public Spot findSpotByPlace(Integer place) {
        String hql = "from Spot where place = :place";
        javax.persistence.Query query = emSpot.createQuery(hql).setParameter("place",place);
        List results = query.getResultList();
        Spot spot = null;
        if (!results.isEmpty()) {
            spot = (Spot) results.get(0);
        }
        return spot;
    }

    private void shortestTicketProcedure(Ticket ticket) {
        LOGGER.info(()->"shortestTicketProcedure for ticket " + ticket + " and shortestTicket: " + shortestTicket);
        ticketSortedSet.add(ticket);
        boolean first_ticket = false;
        if(shortestTicket == null) {
            shortestTicket = ticket;
            LOGGER.info(()->"shortest ticket initialized to " + ticket);
            first_ticket = true;
        }
        else {
            if (shortestTicket.getEnd().isAfter(ticket.getEnd())) {
                shortestTicket = ticket;
                LOGGER.info(() -> "shortest ticket changed to " + ticket);
            } else {
                LOGGER.info(() -> "unchanged");
                return;
            }
        }
        LOGGER.info("Is it first ticket? " + first_ticket);

        if(!first_ticket) {
            LOGGER.info("ticketTimer is canceled: " + ticketTimer.getSchedule());
            ticketTimer.cancel();
        }

        triggerTicketExpirationAction(shortestTicket);
    }


    @Override
    public void sendMessage(String text) {
        jmsContext.createProducer().send(queue,text);
    }

}