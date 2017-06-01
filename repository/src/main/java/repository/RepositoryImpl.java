package repository;

import entity.Spot;
import entity.Ticket;

import javax.annotation.PostConstruct;
import javax.ejb.*;
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

    private EntityManagerFactory emfSpot;
    private EntityManager emSpot;


    @PostConstruct
    public void init() {
        emfSpot = Persistence.createEntityManagerFactory("hibernate-spot-repository");
        emSpot = emfSpot.createEntityManager();
        LOGGER.info(() -> "repository.Repository initialization completed");
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
    @Lock(READ)
    public List<Ticket> getValidTickets() {
        return getValidTicketsWithExpirationBoundary(0);
    }

    @Override
    @Lock(READ)
    public List<Ticket> getValidTicketsWithExpirationBoundary(final Integer EXPIRATION) {
        List<Ticket> tickets = getAllTickets();
        List<Ticket> validTickets = new ArrayList<>(tickets.size());
        validTickets.addAll(tickets.stream().filter(ticket -> ticket.getEnd().plusMinutes(EXPIRATION).isAfterNow()).collect(Collectors.toList()));
        return validTickets;
    }
}