package br.org.tutty.collaborative_whiteboard.cw_web.dtos;

import cw.entities.Project;
import cw.entities.User;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by drferreira on 13/03/15.
 */
public class StoryCreation implements Serializable{

    private Project selectedProject;
    private String code;
    private Date creationDate;
    private User user;
    private String subject;
    private String description;

    public Project getSelectedProject() {
        return selectedProject;
    }

    public void setSelectedProject(Project selectedProject) {
        this.selectedProject = selectedProject;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
