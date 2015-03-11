package backlog_manager.entities;

import backlog_manager.enums.TaskStatus;
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

    public Task(User responsible) {
        this.responsible = responsible;
        this.initDate = new Date();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public User getResponsible() {
        return responsible;
    }

    public void setResponsible(User responsible) {
        this.responsible = responsible;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Date getInitDate() {
        return initDate;
    }

    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
