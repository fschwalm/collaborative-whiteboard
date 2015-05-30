package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import br.org.tutty.collaborative_whiteboard.backlog_manager.services.BacklogManagerService;
import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;
import br.org.tutty.collaborative_whiteboard.cw.service.ProjectService;
import br.org.tutty.collaborative_whiteboard.cw_web.dtos.ProjectData;
import cw.entities.Project;
import cw.exceptions.EmptyEntityException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by drferreira on 11/03/15.
 */
@Named
@ViewScoped
public class ProjectDataController extends GenericController implements Serializable {

    @Inject
    private SessionContext sessionContext;

    @Inject
    private ProjectService projectService;

    @Inject
    private BacklogManagerService backlogManagerService;

    private ProjectData projectData;


    @PostConstruct
    public void setUp() throws CloneNotSupportedException {
        Project selectedProject = sessionContext.getSelectedProject();
        projectData = new ProjectData(selectedProject);
    }

    public String save() throws IOException, CloneNotSupportedException {
        try {
            Project project = (Project) projectData.toEntity();
            projectService.update(project);
        } catch (EmptyEntityException e) {}

        facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_INFO, "project.update");
        return STAY_ON_PAGE;
    }

    public void removeProject() throws IOException {
        facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_WARN, "feature.not_implemented");
    }

    public ProjectData getProjectData() {
        return projectData;
    }

    public void setProjectData(ProjectData projectData) {
        this.projectData = projectData;
    }

}
