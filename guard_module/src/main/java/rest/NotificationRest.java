package rest;

import controllers.SiteBean;
import controllers.SiteBeanImpl;
import message.MessageRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.logging.Logger;

@Path("/notification")
public class NotificationRest {
    private final static Logger LOGGER = Logger.getLogger(NotificationRest.class.toString());

    @Inject
    MessageRepository messageRepository;

    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response test() {
        return Response.status(201).entity("abc").build();
    }

    @POST
    @Path("/message")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response notifyTicket(String data){
        if(data == null || data.isEmpty()){
            return Response.status(400).entity("Please add string!!").build();
        }
        LOGGER.info("right before adding notification to message repository. Message: " + data);
        messageRepository.addMessage(data);
        LOGGER.info("message sent: " + data);
        return Response.status(201).entity(data).build();
    }
}
