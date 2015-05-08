package br.org.tutty.collaborative_whiteboard.cw_web.dtos;

import backlog_manager.entities.Story;
import cw.entities.Project;
import cw.entities.ProjectArea;
import cw.entities.User;
import cw.exceptions.DataNotFoundException;
import java.io.Serializable;

/**
 * Created by drferreira on 13/03/15.
 */
public class StoryCreation implements Serializable{

    private Project selectedProject;
    private String subject;
    private String description;
    private ProjectArea projectArea;

    public Story toEntity(User loggedUser){
        return new Story(loggedUser, selectedProject, projectArea, subject, description);
    }

    public Project getSelectedProject() {
        return selectedProject;
    }

    public void setSelectedProject(Project selectedProject) {
        this.selectedProject = selectedProject;
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

    public ProjectArea getProjectArea() {
        return projectArea;
    }

    public void setProjectArea(ProjectArea projectArea) throws DataNotFoundException {
        this.projectArea = projectArea;
    }
}
