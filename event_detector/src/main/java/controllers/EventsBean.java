package controllers;

import entity.Spot;
import entity.Ticket;

import javax.ejb.Local;
import java.util.List;

@Local
public interface EventsBean {
    public List<Spot> getSpots();
    public List<Ticket> getTickets();
    public List<Ticket> getValidToTenMinutesTickets();
    public Integer getValidTicketsNumber();
    public Integer getOccupationNumber();
    public Integer getTicketNumber();
    public List<String> getNotifications();
    public void addNotification(String info);
}
