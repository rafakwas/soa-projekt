package entity;

import javax.persistence.*;

@Entity
@Table(name = "MESSAGES")
public class Message {

    @Id
    @GeneratedValue
    @Column(updatable=false)
    private Integer id;

    @Column(name = "data")
    private String data;

    public Message() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", data='" + data + '\'' +
                '}';
    }
}


