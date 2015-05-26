package br.org.tutty.collaborative_whiteboard.cw.context;

import backlog_manager.entities.Story;
import cw.dtos.LoggedUser;
import cw.entities.Project;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 * Created by drferreira on 13/03/15.
 */
@SessionScoped
public class SessionContext implements Serializable {
    private LoggedUser loggedUser;
    private Project selectedProject;
    private Story story;

    public LoggedUser getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(LoggedUser loggedUser) {
        this.loggedUser = loggedUser;
    }

    public void setSelectedProject(Project selectedProject) {
        this.selectedProject = selectedProject;
    }

    public Project getSelectedProject() {
        return selectedProject;
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }
}
