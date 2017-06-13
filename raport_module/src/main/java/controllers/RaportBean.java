package controllers;

import generated.Spot;
import generated.Ticket;

import javax.ejb.Local;
import java.util.List;

@Local
public interface RaportBean {
    List<Spot> soap_getAllSpots();
    List<Ticket> soap_getAllTickets();
    Integer soap_getTicketsNumber();
    Integer soap_getSpotsNumber();

    List<entity.Spot> rest_getAllSpots();
    List<entity.Ticket> rest_getAllTickets();
    Integer rest_getTicketsNumber();
    Integer rest_getSpotsNumber();
    List<entity.Ticket> rest_getValidTicketsWithExpirationBoundary(Integer expiration);

    void testString();
}
