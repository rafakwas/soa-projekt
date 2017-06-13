package controllers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import entity.Spot;
import entity.Ticket;
import generated.SoapService;
import generated.SoapServiceService;
import org.joda.time.DateTime;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Logger;

@Singleton
@Startup
@Named(value = "formbean")
public class FormBean implements Serializable {
    private final static Logger LOGGER = Logger.getLogger(FormBean.class.toString());
    private static List<generated.Spot> occupied = new ArrayList<>();
    private Integer id;
    private Integer duration;
    private Integer ticket_place;

    private final static Integer SPOT_WITH_NO_TICKET_EXPIRATION = 2;
    private static final String REST_URL = "http://localhost:8080/main_receiver/api/ticket";
    private Client client;

    public void occupy() {
        generated.DateTime time = new generated.DateTime();
        LOGGER.info(() -> "New spot occupied. Time: " + time);

        for(int i = 0; i < occupied.size(); i++) {
            if (occupied.get(i).getPlace()==id) {
                LOGGER.info("spot already occupied");
                FacesMessage message = new FacesMessage("Spot " + id + " is already occupied");
                FacesContext.getCurrentInstance().addMessage(null, message);
                return;
            }
        }
        generated.Spot spot = new generated.Spot();
        spot.setPlace(id);
        spot.setTime(time);
        occupied.add(spot);

        SoapService service = new SoapServiceService().getSoapServicePort();
        service.notifyOccupation(spot);
    }

    public void vacate(Integer place) {
        LOGGER.info(() -> "Spot vacated. Id: " + place);

        for(int i = 0; i < occupied.size(); i++) {
            if (occupied.get(i).getPlace()==place) {
                occupied.remove(i);
                break;
            }
        }

        SoapService service = new SoapServiceService().getSoapServicePort();
        service.notifyVacation(place);

    }

    public void buyTicket() {

        client = ClientBuilder.newClient();
        WebTarget target = client.target(REST_URL + "/post");

        /* ------------------------PREPARE TICKET JSON ----------------------------*/
        DateTime time = new DateTime();
        LOGGER.info("Mock: ticket start time" + time);
        Ticket ticket = new Ticket();
        ticket.setStart(time);
        ticket.setCost(0.5*duration.doubleValue());
        ticket.setPlace(ticket_place);
        ticket.setEnd(time.plusMinutes(duration));

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setTimeZone(TimeZone.getTimeZone("Europe/Warsaw"));

        ObjectWriter ow = mapper.writer();
        String input= null;
        try {
            input = ow.writeValueAsString(ticket);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        LOGGER.info("Mock Ticket json: " + input);
        /* ------------------------PREPARE TICKET JSON ----------------------------*/
        Invocation.Builder invocationBuilder =  target.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.post(Entity.entity(input, MediaType.APPLICATION_JSON));
    }

    /* GETTERS & SETTERS */

    public List<generated.Spot> getOccupied() {
        return occupied;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {this.duration = duration;}

    public Integer getTicket_place() {
        return ticket_place;
    }

    public void setTicket_place(Integer ticket_place) {
        this.ticket_place = ticket_place;
    }
}
