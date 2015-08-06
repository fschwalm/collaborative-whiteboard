package backlog_manager.entities;

import br.org.tutty.Equalization;
import br.org.tutty.util.PropertyMonitor;
import cw.entities.Stage;
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
    private Long id;

    @Equalization(name = "taks_code")
    private String code;

    @ManyToOne
    private Story story;

    @ManyToOne
    private User author;

    @ManyToOne
    private Stage stage;

    @Equalization(name = "taks_estimated_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estimatedTime;

    @Equalization(name = "taks_subject")
    private String subject;

    @Equalization(name = "taks_description")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Transient
    public PropertyMonitor propertyMonitor = new PropertyMonitor(this);

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        String oldValue = this.code;
        this.code = code;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("code", oldValue, code);
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        Story oldValue = this.story;
        this.story = story;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("story", oldValue, story);
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

    public Long getId() {
        return id;
    }

    public void setAuthor(User author) {
        User oldValue = this.author;
        this.author = author;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("author", oldValue, author);
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        Stage oldValue = this.stage;
        this.stage = stage;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("stage", oldValue, stage);
    }
}
