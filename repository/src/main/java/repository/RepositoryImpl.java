package repository;

import entity.Spot;
import entity.Ticket;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.joda.time.DateTime;
import test.entity.Sometype;
import test.entity.TicketReplica;

import static javax.ejb.LockType.READ;
import static javax.ejb.LockType.WRITE;


@Singleton
@ConcurrencyManagement(value = ConcurrencyManagementType.CONTAINER)
@Local(Repository.class)
@AccessTimeout(value=30000)
@Named(value = "repositoryimpl")
public class RepositoryImpl implements Repository {

    private final static Logger LOGGER = Logger.getLogger(RepositoryImpl.class.toString());

    private EntityManagerFactory emfSpot;
    private EntityManager emSpot;


    @PostConstruct
    public void init() {
        emfSpot = Persistence.createEntityManagerFactory( "hibernate-spot-repository" );
        emSpot = emfSpot.createEntityManager();
        LOGGER.info(()-> "repository.Repository initialization completed");
    }


    public RepositoryImpl() {
    }

    @Override
    @Lock(WRITE)
    public void addSpot() {
        Spot spot = new Spot();
        spot.setPlace(5);
        spot.setTime(new DateTime());

        LOGGER.info(()-> "Add spot:" + spot);
        emSpot.getTransaction().begin();
        if(!emSpot.contains(spot)) {
            emSpot.persist(spot);
            emSpot.flush();
        }
        emSpot.getTransaction().commit();
        LOGGER.info(() -> "Added spot to database: " + spot);
    }

    @Override
    @Lock(WRITE)
    public void removeSpot(Integer place) {
        LOGGER.info(()-> "Remove spot place: " + place);
        Session session = emSpot.unwrap(Session.class);
        String hql = "delete from Spot where place = :place";
        Transaction tx = null;
        tx = session.beginTransaction();
        Query query = session.createQuery(hql).setParameter("place",place);
        query.executeUpdate();
        tx.commit();
        LOGGER.info(() -> "Spot " +place+ "deleted from database");
    }

    @Override
    @Lock(WRITE)
    public void addTicket() {
        Ticket ticket = new Ticket();
        DateTime time = new DateTime();
        ticket.setStart(time);
        ticket.setCost(20.0);
        ticket.setEnd(time.plusMinutes(20));


        LOGGER.info(()-> "Add ticket: " + ticket);
        emSpot.getTransaction().begin();
        LOGGER.info(()-> "Transaction begin: " + ticket);
        if(!emSpot.contains(ticket)) {
            LOGGER.info(()-> "No ticket in database");
            emSpot.persist(ticket);
            LOGGER.info(()-> "Ticket persisted: " +ticket);
            emSpot.flush();
            LOGGER.info(()-> "Flushed: " + ticket);
        }
        emSpot.getTransaction().commit();
        LOGGER.info(()-> "Transaction commited: " + ticket);

        LOGGER.info(() -> "Added ticket to database: " + ticket);

    }

    @Override
    @Lock(READ)
    public List<Spot> getAllSpots() {
        String hql = "from Spot";
        javax.persistence.Query query = emSpot.createQuery(hql);
        return query.getResultList();
    }

    @Override
    @Lock(READ)
    public List<Ticket> getAllTickets() {
        String hql = "from Ticket ";
        javax.persistence.Query query = emSpot.createQuery(hql);
        return query.getResultList();
    }

    @Override
    public void addSomeType() {
        Sometype sometype = new Sometype();
        sometype.setStart(new DateTime());


        LOGGER.info(()-> "Add sometype: " + sometype);
        emSpot.getTransaction().begin();
        LOGGER.info(()-> "Transaction begin: " + sometype);
        if(!emSpot.contains(sometype)) {
            LOGGER.info(()-> "No sometype  in database");
            emSpot.persist(sometype);
            LOGGER.info(()-> "sometype persisted: " +sometype);
            emSpot.flush();
            LOGGER.info(()-> "Flushed: " + sometype);
        }
        emSpot.getTransaction().commit();
        LOGGER.info(()-> "Transaction commited: " + sometype);

        LOGGER.info(() -> "Added sometype to database: " + sometype);


    }
    @Override
    @Lock(WRITE)
    public void addTicketReplica() {
        TicketReplica ticket = new TicketReplica();
        DateTime time = new DateTime();
        ticket.setStart(time);
        ticket.setCost(20.0);
        ticket.setEnd(time.plusMinutes(20));


        LOGGER.info(()-> "Add TicketReplica: " + ticket);
        emSpot.getTransaction().begin();
        LOGGER.info(()-> "Transaction begin: " + ticket);
        if(!emSpot.contains(ticket)) {
            LOGGER.info(()-> "No TicketReplica in database");
            emSpot.persist(ticket);
            LOGGER.info(()-> "TicketReplica persisted: " +ticket);
            emSpot.flush();
            LOGGER.info(()-> "Flushed: " + ticket);
        }
        emSpot.getTransaction().commit();
        LOGGER.info(()-> "Transaction commited: " + ticket);

        LOGGER.info(() -> "Added TicketReplica to database: " + ticket);

    }

}
