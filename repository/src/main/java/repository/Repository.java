package repository;

import entity.Spot;
import entity.Ticket;

import javax.ejb.Local;
import java.util.List;

@Local
public interface Repository {
    void addSpot();
    void removeSpot(Integer place);
    void addTicket();
    void addTicketReplica();
    void addSomeType();
    List<Spot> getAllSpots();
    List<Ticket> getAllTickets();

}
