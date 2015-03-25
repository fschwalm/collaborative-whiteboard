package backlog_manager.entities;


import br.org.tutty.util.PropertyMonitor;
import cw.entities.Project;
import cw.entities.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by drferreira on 11/03/15.
 */
@Entity
@Table(name = "story", catalog = "cw")
@SequenceGenerator(name="Priority_seq", sequenceName="Story_Priority_Seq", allocationSize=1)
public class Story implements Serializable{
    @Id
    private String code;

    @Column
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="Story_Priority_Seq")
    private Integer priority;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @ManyToOne
    private User author;

    @ManyToOne
    private Project project;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "story")
    private List<Task> tasks;

    @OneToMany(mappedBy = "story")
    private List<Comment> comments;

    @Transient
    public PropertyMonitor propertyMonitor = new PropertyMonitor(this);

    public Story(User author, Project project, Date creationDate) {
        this.author = author;
        this.project = project;
        this.creationDate = creationDate;
        this.creationDate = new Date();
    }

    public Story(User author, Project project) {
        this.author = author;
        this.project = project;
        this.creationDate = new Date();
    }

    public Story(User author) {
        this.author = author;
        this.creationDate = new Date();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        String oldValue = this.code;
        this.code = code;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("code", oldValue, code);
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        User oldValue = this.author;
        this.author = author;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("author", oldValue, author);
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        String oldValue = this.subject;
        this.subject = subject;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("subject", oldValue, subject);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        List<Task> oldValue = this.tasks;
        this.tasks = tasks;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("tasks", oldValue, tasks);
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        List<Comment> oldValue = this.comments;
        this.comments = comments;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("comments", oldValue, comments);
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        Project oldValue = this.project;
        this.project = project;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("project", oldValue, project);
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        String oldValue = this.description;
        this.description = description;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("description", oldValue, description);
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        Integer oldValue = this.priority;
        this.priority = priority;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("priority", oldValue, priority);
    }

    @Override
    public Story clone() throws CloneNotSupportedException {
        Story story = new Story(author, project, creationDate);
        story.setCode(code);
        story.setSubject(subject);
        story.setDescription(description);
        story.setPriority(priority);
        story.setComments(comments);
        story.setTasks(tasks);

        return story;
    }
}
