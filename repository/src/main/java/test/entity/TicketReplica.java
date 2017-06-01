package test.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "TicketReplica")
@XmlRootElement
public class TicketReplica {

    @Id
    @GeneratedValue
    @Column(updatable = false)
    private Integer id;

    @Column(name = "start")
    private DateTime start;
    @Column(name = "end_date")
    private DateTime end;
    @Column(name = "cost")
    private Double cost;

    public TicketReplica() {
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
        return "TicketReplica{" +
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

        TicketReplica that = (TicketReplica) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}