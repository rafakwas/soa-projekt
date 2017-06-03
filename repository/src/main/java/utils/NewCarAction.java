package utils;

import java.io.Serializable;

public class NewCarAction implements Serializable {
    private Integer place;

    public NewCarAction(Integer place) {
        this.place = place;
    }

    public Integer getPlace() {
        return place;
    }

    public void setPlace(Integer place) {
        this.place = place;
    }
}
