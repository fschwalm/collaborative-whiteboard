package backlog_manager.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by drferreira on 11/03/15.
 */
@Entity
@Table(name = "comment", catalog = "cw")
public class Comment implements Serializable {
    @Id
    private String id;

    @ManyToOne
    private Story story;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String text;

    public Comment() {
        this.date = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
