package repository;

import entity.Spot;
import entity.Ticket;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


@Singleton
@Local(Repository.class)
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
    public void addSpot(Spot spot) {
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
    public void removeSpot(Integer place) {
        LOGGER.info(()-> "Add spot place: " + place);
        Session session = emSpot.unwrap(Session.class);
        String hql = "delete from Spot where place = :place";
        Transaction tx = null;
        tx = session.beginTransaction();
        Query query = session.createQuery(hql).setParameter("place",place);
        query.executeUpdate();
        tx.commit();
    }

    @Override
    public void addTicket(Ticket ticket) {
        LOGGER.info(()-> "Add ticket: " + ticket);
    }

    @Override
    public List<Spot> getAllSpots() {
        return null;
    }

    @Override
    public List<entity.Ticket> getAllTickets() {
        return null;
    }
}
