package controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import entity.Spot;
import entity.Ticket;
import org.joda.time.DateTime;
import repository.Repository;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSContext;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Logger;


@Stateless
@Named(value = "repotestbean")
public class RepoTestBean implements Serializable {
    private final static Logger LOGGER = Logger.getLogger(RepoTestBean.class.toString());

    private Integer id;
    private Integer duration;
    private Integer ticketPlace;

    @Inject
    Repository repository;


    public void occupy() {
        DateTime time = new DateTime();
        LOGGER.info(() -> "New spot occupied. Place: " + id + ".Time: " + time);

        Spot spot = new Spot();
        spot.setPlace(id);
        spot.setTime(time);

        repository.addSpot(spot);
    }

    public void vacate(Integer place) {
        LOGGER.info(() -> "Spot vacated. Place: " + place);
        repository.removeSpot(place);
    }

    public void buyTicket() {
        Ticket ticket = new Ticket();
        ticket.setStart(DateTime.now());
        ticket.setCost(0.5*duration.doubleValue());
        ticket.setPlace(ticketPlace);
        ticket.setEnd(DateTime.now().plusMinutes(duration));
        repository.addTicket(ticket);
    }

    public void sendTestMessage() {
        repository.sendMessage("TEST TEST TEST TEST");
    }

    /* GETTERS & SETTERS */

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getTicketPlace() {
        return ticketPlace;
    }

    public void setTicketPlace(Integer ticketPlace) {
        this.ticketPlace = ticketPlace;
    }
}
