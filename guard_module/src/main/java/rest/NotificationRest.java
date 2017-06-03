package rest;

import controllers.SiteBean;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/notification")
public class NotificationRest {

    @Inject
    SiteBean siteBean;

    @POST
    @Path("/message")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response notifyTicket(String data){
        if(data == null || data.isEmpty()){
            return Response.status(400).entity("Please add string!!").build();
        }
        siteBean.addNotification(data);
        return Response.status(201).entity(data).build();
    }
}
