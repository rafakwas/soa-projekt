package repository;

import entity.Spot;
import entity.Ticket;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.joda.time.DateTime;

import static javax.ejb.LockType.READ;
import static javax.ejb.LockType.WRITE;


@Singleton
@ConcurrencyManagement(value = ConcurrencyManagementType.CONTAINER)
@Local(Repository.class)
@AccessTimeout(value=30000)
@Named(value = "repositoryimpl")
public class RepositoryImpl implements Repository {
    private final static Logger LOGGER = Logger.getLogger(RepositoryImpl.class.toString());
    private final static Integer EXPIRATION = 10;
    private final static Integer SPOT_WITH_NO_TICKET_EXPIRATION = 10;
    private EntityManagerFactory emfSpot;
    private EntityManager emSpot;

    @Resource
    TimerService timerService;

    @PostConstruct
    public void init() {
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
        triggerTimer(spot.getPlace());
    }

    public void triggerTimer(Integer place) {
        long intervalDuration = SPOT_WITH_NO_TICKET_EXPIRATION*1000;
        LOGGER.info("Setting a programmatic timeout for "
                + intervalDuration + " milliseconds from now. Scheduled for place " + place);
        Timer timer = timerService.createTimer(intervalDuration, place);
    }

    @Timeout
    public void programmaticTimeout(Timer timer) {
        Integer place = (Integer)timer.getInfo();
        LOGGER.info("Programmatic timeout occurred. Looking up to ticket for place " + place + " in database");
        Ticket ticket = findTicketByPlace(place);
        if (ticket == null) {
            LOGGER.info("No ticket found for place " + place + ". Event detector is being informed!");
        }
        else {
            LOGGER.info("Found ticket for place " + place + ". Event detector won't be informed!. Ticket: " + ticket);
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
}