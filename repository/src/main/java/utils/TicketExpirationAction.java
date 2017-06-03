package utils;

import java.io.Serializable;

public class TicketExpirationAction implements Serializable {
    Integer place;

    public TicketExpirationAction(Integer place) {
        this.place = place;
    }

    public Integer getPlace() {
        return place;
    }
}
