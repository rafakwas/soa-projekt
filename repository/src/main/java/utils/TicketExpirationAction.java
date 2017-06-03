package utils;

import java.io.Serializable;

public class TicketExpirationAction implements Serializable {
    Integer id;

    public TicketExpirationAction(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
