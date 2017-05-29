package model;

import org.joda.time.DateTime;

public class Spot {
    private Integer id;
    private DateTime start;

    public Spot() {}

    public Spot(Integer id, DateTime start) {
        this.id = id;
        this.start = start;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DateTime getStart() {
        return start;
    }

    public void setStart(DateTime start) {
        this.start = start;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Spot spot = (Spot) o;

        return id.equals(spot.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
