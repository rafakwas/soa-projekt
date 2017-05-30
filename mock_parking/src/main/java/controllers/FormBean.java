package controllers;

import generated.SoapService;
import generated.SoapServiceService;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Singleton
@Startup
@Named(value = "formbean")
public class FormBean implements Serializable {
    private final static Logger LOGGER = Logger.getLogger(FormBean.class.toString());
    private static List<generated.Spot> occupied = new ArrayList<>();

    private Integer id;

    public void occupy() {
        generated.DateTime time = new generated.DateTime();
        LOGGER.info(() -> "New spot occupied. Time: " + time);

        generated.Spot spot = new generated.Spot();
        spot.setPlace(id);
        spot.setTime(time);
        occupied.add(spot);

        SoapService service = new SoapServiceService().getSoapServicePort();
        service.notifyOccupation(spot);
    }

    public void vacate(Integer place) {
        LOGGER.info(() -> "Spot vacated. Id: " + id);

        for(int i = 0; i < occupied.size(); i++) {
            if (occupied.get(i).getPlace()==id) {
                occupied.remove(i);
                break;
            }
        }

        SoapService service = new SoapServiceService().getSoapServicePort();
        service.notifyVacation(place);

    }

    /* GETTERS & SETTERS */

    public List<generated.Spot> getOccupied() {
        return occupied;
    }

    public void setOccupied(List<generated.Spot> occupied) {
        FormBean.occupied = occupied;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
