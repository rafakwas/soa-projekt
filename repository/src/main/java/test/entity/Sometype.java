package test.entity;

        import com.fasterxml.jackson.annotation.JsonFormat;
        import org.joda.time.DateTime;
        import javax.persistence.*;
        import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "Sometype")
@XmlRootElement
public class Sometype{

    @Id
    @GeneratedValue
    @Column(updatable = false)
    private Integer id;

    @Column(name = "start")
    private DateTime start;

    public Sometype() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sometype sometype = (Sometype) o;

        return id != null ? id.equals(sometype.id) : sometype.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}