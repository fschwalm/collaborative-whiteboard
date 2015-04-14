package br.org.tutty.collaborative_whiteboard.cw_web.dtos;

import cw.entities.Project;
import cw.exceptions.DataNotFoundException;
import java.io.Serializable;

/**
 * Created by drferreira on 13/03/15.
 */
public class StoryCreation implements Serializable{

    private Project selectedProject;
    private String subject;
    private String description;
    private String projectArea;

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

    public String getProjectArea() {
        return projectArea;
    }

    public void setProjectArea(String projectArea) throws DataNotFoundException {
        this.projectArea = projectArea;
    }
}
