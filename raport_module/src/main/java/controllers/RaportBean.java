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
}
