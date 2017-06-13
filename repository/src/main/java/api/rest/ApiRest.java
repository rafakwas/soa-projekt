package api.rest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import entity.Spot;
import entity.Ticket;
import repository.Repository;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.jws.WebParam;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Path("/external")
public class ApiRest {

    @Inject
    Repository repository;

    @GET
    @Path("/test")
    public String getTestString() {
        return "testttt";
    }

    @GET
    @Path("/array")
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

    @GET
    @Path("/alltickets")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTickets() {
        List<Ticket> tickets = repository.getAllTickets();
        List<String> ticketsJson = new ArrayList<>(tickets.size());

        for(Ticket ticket : tickets) {
            ticketsJson.add(objectToString(ticket));
        }

        GenericEntity<List<String>> entities = new GenericEntity<List<String>>(ticketsJson){};
        return Response.status(201).entity(entities).build();
    }

    @GET
    @Path("/expirationtickets/{expiration}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTickets(@PathParam("expiration") String expiration) {
        Integer integer = null;
        try {
            integer = Integer.parseInt(expiration);
        } catch (NumberFormatException ex) {
            return Response.status(400).entity("wrong expiration format").build();
        }
        List<Ticket> tickets = repository.getValidTicketsWithExpirationBoundary(integer);
        List<String> ticketsJson = new ArrayList<>(tickets.size());

        for(Ticket ticket : tickets) {
            ticketsJson.add(objectToString(ticket));
        }

        GenericEntity<List<String>> entities = new GenericEntity<List<String>>(ticketsJson){};
        return Response.status(201).entity(entities).build();
    }


    @GET
    @Path("/allspots")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSpots() {
        List<Spot> spots = repository.getAllSpots();
        List<String> spotsJson = new ArrayList<>(spots.size());

        for (Spot spot : spots) {
            spotsJson.add(objectToString(spot));
        }

        GenericEntity<List<String>> entities = new GenericEntity<List<String>>(spotsJson){};

        return Response.status(201).entity(entities).build();
    }

    @GET
    @Path("/ticketsnumber")
    public Integer getTicketsNumber() {
        return repository.getAllTickets().size();
    }

    @GET
    @Path("/spotsnumber")
    public Integer getSpotsNumber() {
        return repository.getAllSpots().size();
    }

    private String objectToString(Object t) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ObjectWriter ow = mapper.writer();
        String output = null;
        try {
            output = ow.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return output;
    }

}
