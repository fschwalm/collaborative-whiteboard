package backlog_manager.entities;

import backlog_manager.enums.TaskStatus;
import br.org.tutty.util.PropertyMonitor;
import cw.entities.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by drferreira on 11/03/15.
 */

@Entity
@Table(name = "task", catalog = "cw")
public class Task implements Serializable {
    @Id
    private String code;

    @ManyToOne
    private User responsible;

    @ManyToOne
    private Story story;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus taskStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "init_date", nullable = false)
    private Date initDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Transient
    public PropertyMonitor propertyMonitor = new PropertyMonitor(this);

    public Task(User responsible) {
        this.responsible = responsible;
        this.initDate = new Date();
    }
}
