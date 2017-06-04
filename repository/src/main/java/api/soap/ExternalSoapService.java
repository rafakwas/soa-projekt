package api.soap;

import entity.Spot;
import entity.Ticket;
import repository.Repository;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;
import java.util.logging.Logger;

@WebService
public class ExternalSoapService {
    private final static Logger LOGGER = Logger.getLogger(ExternalSoapService.class.toString());

    @Inject
    Repository repository;

    @WebMethod
    public List<Spot> getAllSpots() {
        LOGGER.info("getAllSpots soap");
        return repository.getAllSpots();
    }

    @WebMethod
    public List<Ticket> getAllTickets() {
        LOGGER.info("getAllTickets soap");
        return repository.getAllTickets();
    }

    @WebMethod
    public Integer getTicketsNumber() {
        LOGGER.info("getTicketsNumber soap");
        return repository.getAllTickets().size();
    }

    @WebMethod
    public Integer getAllSpotsNumber() {
        LOGGER.info("getAllSpotsNumber soap");
        return repository.getAllSpots().size();
    }

    @WebMethod
    public Ticket findTicketByPlace(Integer place) {
        LOGGER.info("findTicketByPlace soap");
        return repository.findTicketByPlace(place);
    }

    @WebMethod
    public Spot findSpotByPlace(Integer place) {
        LOGGER.info("findSpotByPlace soap");
        return repository.findSpotByPlace(place);
    }



}


