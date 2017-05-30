package rest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.sun.org.apache.xml.internal.utils.URI;
import entity.Ticket;
import org.joda.time.DateTime;
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

@Path("/ticket")
public class TicketRest {

    @Inject
    private TicketService ticketService;

    @GET
    @Path("info/{index}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTicket(@PathParam("index") String index){
        Ticket ticket = new Ticket();
        ticket.setId(1);
        ticket.setStart(DateTime.now());
        ticket.setCost(20.0);
        ticket.setEnd(DateTime.now().plusHours(1));

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ObjectWriter ow = mapper.writer();
        String result = null;
        try {
            result = ow.writeValueAsString(ticket);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTickets(){
        List<Ticket> tickets = new ArrayList<>();

        Ticket ticket1 = new Ticket();
        ticket1.setId(1);
        ticket1.setStart(DateTime.now());
        ticket1.setCost(10.0);
        ticket1.setEnd(DateTime.now().plusHours(1));

        Ticket ticket2 = new Ticket();
        ticket2.setId(2);
        ticket2.setStart(DateTime.now());
        ticket2.setCost(20.0);
        ticket2.setEnd(DateTime.now().plusHours(2));

        Collections.addAll(tickets,ticket1,ticket2);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ObjectWriter ow = mapper.writer();

        String result = null;
        try {
            result = ow.writeValueAsString(tickets);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray getAll() {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        List<Integer> list = new ArrayList<Integer>();
        Collections.addAll(list,1,1,2,3,4);
        for (Integer i : list) {
            builder.add(Json.createObjectBuilder().add("number",i));
        }
        return builder.build();
    }

    @DELETE
    @Path("/{index}")
    public void deleteStudent(@PathParam("index") String index){
        ticketService.deleteTicket(Integer.parseInt(index));
    }

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

        String output = ticket.getStart().getHourOfDay()+":"+ticket.getStart().getMinuteOfHour()+":"+ticket.getStart().getSecondOfMinute();


        return Response.status(201).entity(output).build();
    }

}