package rest;

import entity.Ticket;
import service.ticket.TicketService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/ticket")
public class TicketRest {

    @Inject
    private TicketService ticketService;

    @GET
    @Path("/{index}")
    public Ticket getTicket(@PathParam("index") String index){
        return ticketService.findTicket(Integer.parseInt(index));
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Ticket> getTickets(){
        return ticketService.getAllTickets();
    }

    @DELETE
    @Path("/{index}")
    public void deleteStudent(@PathParam("index") String index){
        ticketService.deleteTicket(Integer.parseInt(index));
    }

}
