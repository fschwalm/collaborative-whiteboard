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
@SequenceGenerator(name = "TaskSequence", sequenceName = "task_seq", initialValue = 1, allocationSize = 1)
public class Task implements Serializable {

    @Id
    @GeneratedValue(generator = "TaskSequence", strategy = GenerationType.SEQUENCE)
    private String code;

    @ManyToOne
    private User responsible;

    @ManyToOne
    private Story story;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus taskStatus;

    @Temporal(TemporalType.TIMESTAMP)
    private Date estimatedTime;

    private String subject;

    private String description;

    @Transient
    public PropertyMonitor propertyMonitor = new PropertyMonitor(this);

    public Task() {
    }

    public Task(User responsible) {
        this.responsible = responsible;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        String oldValue = this.code;
        this.code = code;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("code", oldValue, code);
    }

    public User getResponsible() {
        return responsible;
    }

    public void setResponsible(User responsible) {
        User oldValue = this.responsible;
        this.responsible = responsible;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("responsible", oldValue, responsible);
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        Story oldValue = this.story;
        this.story = story;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("story", oldValue, story);
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        TaskStatus oldValue = this.taskStatus;
        this.taskStatus = taskStatus;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("taskStatus", oldValue, taskStatus);
    }

    public void setEstimatedTime(Date estimatedTime) {
        Date oldValue = this.estimatedTime;
        this.estimatedTime = estimatedTime;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("estimatedTime", oldValue, estimatedTime);
    }

    public Date getEstimatedTime() {
        return estimatedTime;
    }

    public void setSubject(String subject) {
        String oldValue = this.subject;
        this.subject = subject;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("subject", oldValue, subject);
    }

    public String getSubject() {
        return subject;
    }

    public void setDescription(String description) {
        String oldValue = this.description;
        this.description = description;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("description", oldValue, description);
    }

    public String getDescription() {
        return description;
    }
}
