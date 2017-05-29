package controllers;

import model.Spot;
import org.joda.time.DateTime;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
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
    private static List<Spot> occupied = new ArrayList<>();

    private Integer id;

    public void occupy() {
        DateTime time = new DateTime();
        LOGGER.info(() -> "New spot occupied. Time: " + time);
        occupied.add(new Spot(id,time));
    }

    public void vacate(Integer id) {
        LOGGER.info(() -> "Spot vacated. Id: " + id);
        occupied.remove(new Spot(id,null));
    }

    /* GETTERS & SETTERS */
    public List<Spot> getOccupied() {
        return occupied;
    }

    public void setOccupied(List<Spot> occupied) {
        FormBean.occupied = occupied;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
