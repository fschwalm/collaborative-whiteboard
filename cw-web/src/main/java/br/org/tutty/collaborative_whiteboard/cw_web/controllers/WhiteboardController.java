package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;
import br.org.tutty.collaborative_whiteboard.cw.service.ProjectService;
import br.org.tutty.collaborative_whiteboard.cw.service.UserService;
import cw.entities.Project;
import cw.exceptions.DataNotFoundException;
import cw.exceptions.EncryptedException;
import cw.exceptions.NameInUseException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by drferreira on 15/03/15.
 */
@Named
@ViewScoped
public class WhiteboardController extends GenericController implements Serializable {

    private String projectName;

    @Inject
    private SessionContext sessionContext;

    @Inject
    private ProjectService projectService;

    private List<Project> projects;

    @PostConstruct
    public void setUp(){
        projectName = null;
        projects = fetchProjects();
    }

    public void createProject() throws IOException {
        try {
            projectService.createProject(projectName);
            setUp();
            showGlobalMessageWithoutDetail(FacesMessage.SEVERITY_INFO, "whiteboard.project_created");

        } catch (NameInUseException e) {
            showGlobalMessageWithoutDetail(FacesMessage.SEVERITY_WARN, "whiteboard.name_in_use");
        }
    }

    public List<Project> fetchProjects() {
        try {
            return projectService.fetchProjects();

        } catch (DataNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public String openProject(Project project){
        sessionContext.setSelectedProject(project);
        return GOT_TO_PROJECT_PAGE;
    }

    public String goToBacklog(){
        return GO_TO_BACKLOG;
    }

    public Boolean hasSomeProject(){
        return !projects.isEmpty();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}
