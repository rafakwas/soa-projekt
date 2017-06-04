package controllers;

import generated.ExternalSoapService;
import generated.ExternalSoapServiceService;

import generated.Ticket;
import generated.Spot;

import javax.ejb.*;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

@Stateless
@Named(value = "raportbeanimpl")
@Local
public class RaportBeanImpl implements Serializable,RaportBean{
    private final static Logger LOGGER = Logger.getLogger(RaportBeanImpl.class.toString());

    private static final String REST_URL = "http://localhost:8080/repository/api/external";
    private Client client;

    public void testString() {
        client = ClientBuilder.newClient();
        WebTarget target = client.target(REST_URL + "/test");
        Invocation.Builder invocationBuilder =  target.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        pushMessage(response.readEntity(String.class));
    }


    /*------------------SOAP----------------------*/
    @Override
    public Integer soap_getTicketsNumber() {
        ExternalSoapService service = new ExternalSoapServiceService().getExternalSoapServicePort();
        Integer response = service.getTicketsNumber();
        pushMessage("Tickets number: " + response);
        return response;
    }
    @Override
    public List<Ticket> soap_getAllTickets() {
        ExternalSoapService service = new ExternalSoapServiceService().getExternalSoapServicePort();
        List<Ticket> tickets = service.getAllTickets();
        pushMessage("Tickets: " + tickets);
        return tickets;
    }
    @Override
    public List<Spot> soap_getAllSpots() {
        ExternalSoapService service = new ExternalSoapServiceService().getExternalSoapServicePort();
        List<Spot> spots = service.getAllSpots();
        pushMessage("Spots: " + spots);
        return spots;
    }

    @Override
    public Integer soap_getSpotsNumber() {
        ExternalSoapService service = new ExternalSoapServiceService().getExternalSoapServicePort();
        Integer response = service.getAllSpotsNumber();
        pushMessage("Spots number: " + response);
        return response;
    }

    /*------------------UTILS----------------------*/
    public void pushMessage(String text) {
        FacesMessage message = new FacesMessage(text);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
