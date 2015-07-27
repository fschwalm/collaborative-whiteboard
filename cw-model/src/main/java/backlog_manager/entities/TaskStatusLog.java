package backlog_manager.entities;

import backlog_manager.enums.TaskStatus;
import cw.entities.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by drferreira on 27/07/15.
 */
@Entity
@Table(name = "task_status", catalog = "cw")
@SequenceGenerator(name = "TaskStatusSequence", sequenceName = "task_status_seq", initialValue = 1, allocationSize = 1)
public class TaskStatusLog implements Serializable{

    @Id
    @GeneratedValue(generator = "TaskStatusSequence", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne
    private User user;

    @ManyToOne
    private Task task;

    public TaskStatusLog() {
    }

    public TaskStatusLog(TaskStatus taskStatus, User user, Task task) {
        this.taskStatus = taskStatus;
        this.user = user;
        this.task = task;
        this.date = new Date();
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public Date getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    public Task getTask() {
        return task;
    }
}
