package service.ticket;

import entity.Ticket;

import java.util.List;

public interface TicketService {
    Ticket findTicket(Integer index);
    List<Ticket> getAllTickets();
    boolean deleteTicket(Integer index);
}
