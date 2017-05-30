package rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import entity.Ticket;
import org.joda.time.DateTime;
import service.ticket.TicketService;


import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;


import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Path("/ticket")
public class TicketRest {

    @Inject
    private TicketService ticketService;

    @GET
    @Path("info/{index}")
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
    public List<Ticket> getTickets(){
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
        return tickets;
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
}
