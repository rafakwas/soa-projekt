package entity;


import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Table(name = "Spot")
public class Spot {

    @Id
    @GeneratedValue
    @Column(updatable=false)
    private Integer id;

    @Column(name = "place")
    private Integer place;
    @Column(name = "time")
    private DateTime time;

    public Spot() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlace() {
        return place;
    }

    public void setPlace(Integer place) {
        this.place = place;
    }

    public DateTime getTime() {
        return time;
    }

    public void setTime(DateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Spot{" +
                "id=" + id +
                ", place=" + place +
                ", time=" + time +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Spot spot = (Spot) o;

        return place.equals(spot.place);

    }

    @Override
    public int hashCode() {
        return place.hashCode();
    }
}
