package entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "Ticket")
@XmlRootElement
public class Ticket{

    @Id
    @GeneratedValue
    @Column(updatable = false)
    private Integer id;

    @Column(name = "start_date")
    private DateTime start;
    @Column(name = "end_date")
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public DateTime getStart() {
        return start;
    }

    public void setStart(DateTime start) {
        this.start = start;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", start=" + start +
                ", end=" + end +
                ", cost=" + cost +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        return id.equals(ticket.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}