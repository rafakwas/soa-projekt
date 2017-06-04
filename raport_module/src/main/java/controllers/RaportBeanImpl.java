package controllers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import generated.ExternalSoapService;
import generated.ExternalSoapServiceService;

import generated.Ticket;
import generated.Spot;
import org.joda.time.DateTime;

import javax.ejb.*;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.ws.rs.client.*;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
@Named(value = "raportbeanimpl")
@Local
public class RaportBeanImpl implements Serializable,RaportBean{
    private final static Logger LOGGER = Logger.getLogger(RaportBeanImpl.class.toString());

    private static final String REST_URL = "http://localhost:8080/repository/api/external";
    private Client client;

    @Override
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

    /*------------------REST-----------------------*/

    @Override
    public List<entity.Spot> rest_getAllSpots() {
        client = ClientBuilder.newClient();
        WebTarget target = client.target(REST_URL + "/allspots");
        Invocation.Builder invocationBuilder =  target.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        List<String> spotsJson = response.readEntity(new GenericType<List<String>>(){});
        List<entity.Spot> spots = mapStringsToSpots(spotsJson);
        pushMessage(""+spots);
        return spots;
    }

    @Override
    public List<entity.Ticket> rest_getAllTickets() {
        client = ClientBuilder.newClient();
        WebTarget target = client.target(REST_URL + "/alltickets");
        Invocation.Builder invocationBuilder =  target.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        List<String> ticketsJson = response.readEntity(new GenericType<List<String>>(){});
        List<entity.Ticket> tickets = mapStringsToTickets(ticketsJson);
        pushMessage(""+tickets);
        return tickets;
    }

    @Override
    public Integer rest_getTicketsNumber() {
        client = ClientBuilder.newClient();
        WebTarget target = client.target(REST_URL + "/ticketsnumber");
        Invocation.Builder invocationBuilder =  target.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        Integer tickets_number = response.readEntity(Integer.class);
        pushMessage("Rest ticket number: " + tickets_number);
        return tickets_number;
    }
    @Override
    public Integer rest_getSpotsNumber() {
        client = ClientBuilder.newClient();
        WebTarget target = client.target(REST_URL + "/spotsnumber");
        Invocation.Builder invocationBuilder = target.request();
        Response response = invocationBuilder.get();
        Integer spots_number = response.readEntity(Integer.class);
        pushMessage("Rest spots number: " + spots_number);
        return spots_number;
    }
    /*------------------UTILS----------------------*/
    private void pushMessage(String text) {
        FacesMessage message = new FacesMessage(text);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    private List<entity.Ticket> mapStringsToTickets(List<String> ticketsJson) {
        List<entity.Ticket> tickets = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());

        for(String json : ticketsJson) {
            entity.Ticket ticket = null;
            try {
                ticket = mapper.readValue(json, entity.Ticket.class);
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            tickets.add(ticket);
        }
        return tickets;
    }
    private List<entity.Spot> mapStringsToSpots(List<String> spotsJson) {
        List<entity.Spot> spots = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());

        for(String json : spotsJson) {
            entity.Spot spot = null;
            try {
                spot = mapper.readValue(json, entity.Spot.class);
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            spots.add(spot);
        }
        return spots;
    }


}
