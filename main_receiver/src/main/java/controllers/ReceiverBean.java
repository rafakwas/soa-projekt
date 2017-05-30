package controllers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
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
import java.io.IOException;
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
        getTickets();
    }

    public void getTicket() {
        client = ClientBuilder.newClient();
        WebTarget target = client.target(REST_URL + "/info/1");
        String jsonString = target.request(MediaType.APPLICATION_JSON).get(String.class);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());

        Ticket ticket = null;
        try {
            ticket = mapper.readValue(jsonString, Ticket.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        notifications.add(ticket.toString());
    }

    public void getTickets() {
        client = ClientBuilder.newClient();
        WebTarget target = client.target(REST_URL + "/all");

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());

        String jsonString = target.request(MediaType.APPLICATION_JSON).get(String.class);

        List<Ticket> myObjects = null;
        try {
            myObjects = mapper.readValue(jsonString, new TypeReference<List<Ticket>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOGGER.info("received list: " + myObjects.toString());
        Ticket ticket = myObjects.get(0);
        LOGGER.info("received list: " + myObjects.toString());
        notifications.add(myObjects.toString());
    }
}
