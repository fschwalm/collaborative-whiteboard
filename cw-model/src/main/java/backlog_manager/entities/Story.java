package backlog_manager.entities;


import br.org.tutty.util.PropertyMonitor;
import cw.entities.Project;
import cw.entities.ProjectArea;
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
@SequenceGenerator(name = "StorySequence", sequenceName = "story_seq", initialValue = 1, allocationSize = 1)
public class Story implements Serializable{

    @Id
    @GeneratedValue(generator = "StorySequence", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String branch;

    @Column(nullable = false)
    private Integer priority;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @ManyToOne
    private User author;

    @ManyToOne
    private Project project;

    @ManyToOne
    private ProjectArea projectArea;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Boolean removed;

    @OneToMany(mappedBy = "story")
    private List<Task> tasks;

    @Transient
    public PropertyMonitor propertyMonitor = new PropertyMonitor(this);

    public Story(User author, Project project, Date creationDate) {
        this.author = author;
        this.project = project;
        this.creationDate = creationDate;
        this.creationDate = new Date();
        this.removed = false;
    }

    public Story(User author, Project project) {
        this.author = author;
        this.project = project;
        this.creationDate = new Date();
        this.removed = false;
    }

    public Story() {
    }

    public Story(User author) {
        this.author = author;
        this.creationDate = new Date();
        this.removed = false;
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

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        Long oldValue = this.id;
        this.id = id;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("id", oldValue, id);
    }

    public void setPriority(Integer priority) {
        Integer oldValue = this.priority;
        this.priority = priority;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("priority", oldValue, priority);
    }

    public ProjectArea getProjectArea() {
        return projectArea;
    }

    public String getBranch() {
        return branch;
    }

    public void remove() {
        Boolean oldValue = this.removed;
        this.removed = true;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("removed", oldValue, removed);
    }

    public void restore() {
        Boolean oldValue = this.removed;
        this.removed = false;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("removed", oldValue, removed);
    }

    public void setBranch(String branch) {
        String oldValue = this.branch;
        this.branch = branch;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("branch", oldValue, branch);
    }

    public void setProjectArea(ProjectArea projectArea) {
        this.projectArea = projectArea;
    }

    public Boolean isRemoved() {
        return removed;
    }

    @Override
    public Story clone() throws CloneNotSupportedException {
        Story story = new Story(author, project, creationDate);
        story.setId(id);
        story.setCode(code);
        story.setSubject(subject);
        story.setDescription(description);
        story.setPriority(priority);
        story.setTasks(tasks);
        story.setBranch(branch);

        return story;
    }
}
