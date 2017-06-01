package controllers;

import entity.Spot;
import entity.Ticket;
import repository.Repository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
@Named("eventsbean")
public class EventsBean {
    private final static Logger LOGGER = Logger.getLogger(EventsBean.class.toString());

    @Inject
    Repository repository;

    private List<Ticket> tickets = new ArrayList<>();
    private List<Ticket> validTickets = new ArrayList<>();
    private List<Spot> spots = new ArrayList<>();

    private List<String> notifications = new ArrayList<>();

    public void addNotification(String info) {
        notifications.add(info);
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<String> notifications) {
        this.notifications = notifications;
    }

    public List<Spot> getSpots() {
        spots = repository.getAllSpots();
        return spots;
    }

    public List<Ticket> getTickets() {
        tickets = repository.getAllTickets();
        return tickets;
    }

    public List<Ticket> getValidToTenMinutesTickets() {
        validTickets = repository.getValidTicketsWithExpirationBoundary(1);
        return validTickets;
    }

    public Integer getValidTicketsNumber() { return validTickets.size(); }

    public Integer getOccupationNumber() { return  spots.size(); }

    public Integer getTicketNumber() { return  tickets.size(); }
}
