package service.ticket;

import entity.Ticket;

import java.util.List;

@Deprecated
public interface TicketService {
    Ticket findTicket(Integer index);
    List<Ticket> getAllTickets();
    boolean deleteTicket(Integer index);
}
