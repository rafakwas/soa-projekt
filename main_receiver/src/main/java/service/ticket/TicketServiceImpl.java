package service.ticket;

import entity.Ticket;
import org.joda.time.DateTime;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@Stateless
@Local(TicketService.class)
public class TicketServiceImpl implements TicketService{
    private final static Logger LOGGER = Logger.getLogger(TicketServiceImpl.class.toString());

    private EntityManagerFactory emf;
    private EntityManager em;

    @PostConstruct
    public void init() {
        emf = Persistence.createEntityManagerFactory( "hibernate-spot" );
        em = emf.createEntityManager();
        LOGGER.info(()-> "Initialization completed");
    }

    @Override
    public Ticket findTicket(Integer index) {
        Ticket ticket = new Ticket();
        ticket.setId(1);
        ticket.setStart(DateTime.now());
        ticket.setCost(20.0);
        ticket.setEnd(DateTime.now().plusHours(1));
        return ticket;
    }

    @Override
    public List<Ticket> getAllTickets() {
        List<Ticket> tickets = new ArrayList<>();

        Ticket ticket1 = new Ticket();
        ticket1.setId(1);
        ticket1.setStart(DateTime.now());
        ticket1.setCost(10.0);
        ticket1.setEnd(DateTime.now().plusHours(1));

        Ticket ticket2 = new Ticket();
        ticket2.setId(2);
        ticket2.setStart(DateTime.now());
        ticket2.setCost(20.0);
        ticket2.setEnd(DateTime.now().plusHours(2));

        Collections.addAll(tickets,ticket1,ticket2);
        return tickets;
    }

    @Override
    public boolean deleteTicket(Integer index) {
        return false;
    }
}
