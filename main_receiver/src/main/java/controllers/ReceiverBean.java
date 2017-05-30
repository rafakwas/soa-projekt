package controllers;

import entity.Spot;
import entity.Ticket;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.json.JsonArray;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
@Named("receiverbean")
public class ReceiverBean {
    private final static Logger LOGGER = Logger.getLogger(ReceiverBean.class.toString());

    private EntityManagerFactory emf;
    private EntityManager em;

    @PostConstruct
    public void init() {
        emf = Persistence.createEntityManagerFactory( "hibernate-spot" );
        em = emf.createEntityManager();
        LOGGER.info(()-> "Initialization completed");
    }

    private List<String> notifications = new ArrayList<>();

    public void addNotification(String info) {
        notifications.add(info);
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<String> notifications) {
        this.notifications = notifications;
    }

    public void addSpot(model.Spot spot) {

        entity.Spot persisted = new entity.Spot();
        persisted.setPlace(spot.getId());
        persisted.setTime(spot.getStart());

        em.getTransaction().begin();
        if(!em.contains(persisted)) {
            em.persist(persisted);
            em.flush();
        }
        em.getTransaction().commit();
        LOGGER.info(() -> "Added spot to database: " + persisted);

        String hql = "from Spot where id = :id";
        javax.persistence.Query query = em.createQuery(hql).setParameter("id",spot.getId());
        entity.Spot result = (entity.Spot)query.getSingleResult();
    }

    public void removeSpot(Integer place) {
        Session session = em.unwrap(Session.class);
        String hql = "delete from Spot where place = :place";
        Transaction tx = null;
        tx = session.beginTransaction();
        Query query = session.createQuery(hql).setParameter("place",place);
        query.executeUpdate();
        tx.commit();
    }

    public List<entity.Spot> getSpots() {
        String hql = "from Spot";
        javax.persistence.Query query = em.createQuery(hql);
        return query.getResultList();
    }



    private static final String REST_URL = "http://localhost:8080/main_receiver/api/ticket";
    private Client client;

    public void submit() {
        LOGGER.info(() -> "submit method start");
        getTicket();
    }

    public void getTicket() {
        client = ClientBuilder.newClient();
        WebTarget target = client.target(REST_URL + "/info/1");
        Ticket ticket = target.request(MediaType.APPLICATION_JSON).get(Ticket.class);
        LOGGER.info(()-> "received ticket: " + ticket);
        notifications.add("received ticket: " + ticket);
    }

    public void getTickets() {
        client = ClientBuilder.newClient();
        WebTarget target = client.target(REST_URL + "/all");
        List<Ticket> tickets= target.request(MediaType.APPLICATION_JSON).get(List.class);
        LOGGER.info(()-> "received list of tickets: " + tickets);
        Ticket ticket = tickets.get(0);
//        notifications.add("id: " + ticket.getId() + " .... start " + ticket.getStart().getHourOfDay()  + ":" + ticket.getStart().getMinuteOfHour() + ":" + ticket.getStart().getSecondOfMinute());
    }
}
