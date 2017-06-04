package api.rest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import entity.Spot;
import entity.Ticket;
import repository.Repository;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.*;
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
    public List<Ticket> getAllTickets() {
        return repository.getAllTickets();
    }

    @GET
    @Path("/allspots")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Spot> getAllSpots() {
        return repository.getAllSpots();
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

}
