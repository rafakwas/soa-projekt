package controllers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import entity.Spot;
import entity.Ticket;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jboss.resteasy.client.jaxrs.internal.ClientResponse;
import org.joda.time.DateTime;
import repository.Repository;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.JsonArray;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
@Named("receiverbean")
public class ReceiverBean {
    private final static Logger LOGGER = Logger.getLogger(ReceiverBean.class.toString());

    private final static Integer VALID_MINUTES = 1;

    @Inject
    Repository repository;

    private List<Ticket> tickets = new ArrayList<>();
    private List<Ticket> validTickets = new ArrayList<>();
    private List<Spot> spots = new ArrayList<>();

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

    public List<Spot> getSpots() {
        spots = repository.getAllSpots();
        return spots;
    }

    public List<Ticket> getTickets() {
        tickets = repository.getAllTickets();
        return tickets;
    }

    public List<Ticket> getValidToTenMinutesTickets() {
        validTickets = repository.getValidTicketsWithExpirationBoundary(VALID_MINUTES);
        return validTickets;
    }

    public Integer getValidTicketsNumber() { return validTickets.size(); }

    public Integer getOccupationNumber() { return  spots.size(); }

    public Integer getTicketNumber() { return  tickets.size(); }
}
