package repository;

import entity.Spot;
import entity.Ticket;

import javax.ejb.Local;
import java.util.List;

@Local
public interface Repository {
    void addSpot(Spot spot);
    void removeSpot(Integer place);
    void addTicket(Ticket ticket);
    List<Spot> getAllSpots();
    List<Ticket> getAllTickets();
}
