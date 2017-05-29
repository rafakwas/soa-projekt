package entity;

import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Table(name = "Ticket")
public class Ticket{

    @Id
    @GeneratedValue
    @Column(updatable = false)
    private Integer id;

    @Column(name = "start")
    private DateTime start;
    @Column(name = "end")
    private DateTime end;
    @Column(name = "cost")
    private Double cost;

    public Ticket() {
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

    public DateTime getEnd() {
        return end;
    }

    public void setEnd(DateTime end) {
        this.end = end;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}