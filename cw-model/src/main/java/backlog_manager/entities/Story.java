package backlog_manager.entities;


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
public class Story implements Serializable{
    @Id
    private String code;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @ManyToOne
    private User author;

    @ManyToOne
    private Project project;

    @Column(nullable = false)
    private String subject;

    @OneToMany(mappedBy = "story")
    private List<Task> tasks;

    @OneToMany(mappedBy = "story")
    private List<Comment> comments;

    public Story(User author, Project project) {
        this.author = author;
        this.project = project;
        this.creationDate = new Date();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}
