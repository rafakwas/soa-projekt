package rest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.sun.org.apache.xml.internal.utils.URI;
import controllers.ReceiverBean;
import entity.Ticket;
import org.joda.time.DateTime;
import repository.Repository;
import service.ticket.TicketService;


import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;


import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@Path("/ticket")
public class TicketRest {
    private final static Logger LOGGER = Logger.getLogger(TicketRest.class.toString());

    @Inject
    Repository repository;

    @Inject
    ReceiverBean receiverBean;

    /*----------------PUT IT SOMEWHERE ELSE----------------------- */
    @POST
    @Path("/notification")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response notifyNotification(String data) {
        receiverBean.addNotification(data);
        return Response.status(201).entity(data).build();
    }
    /*------------------------------------------------------------ */

    @POST
    @Path("/post")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response notifyTicket(String data){

        if(data == null){
            return Response.status(400).entity("Please add string!!").build();
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

        Ticket ticket = null;
        try {
            ticket = mapper.readValue(data, Ticket.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        repository.addTicket(ticket);

        String output = ticket.getStart().getHourOfDay()+":"+ticket.getStart().getMinuteOfHour()+":"+ticket.getStart().getSecondOfMinute();
        return Response.status(201).entity(output).build();
    }
}
