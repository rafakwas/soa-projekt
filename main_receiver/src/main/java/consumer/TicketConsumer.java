package consumer;

import entity.Ticket;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class TicketConsumer {

    private static final String REST_URL = "http://localhost:8080/main_receiver/api/ticket";
    private Client client;

    public Ticket getTicket(String index) {
        client = ClientBuilder.newClient();
        WebTarget target = client.target(REST_URL + "/" + index);
        Ticket ticket = target
                .path(index)
                .request(MediaType.APPLICATION_JSON)
                .get(Ticket.class);
        client.close();
        return ticket;
    }

}
