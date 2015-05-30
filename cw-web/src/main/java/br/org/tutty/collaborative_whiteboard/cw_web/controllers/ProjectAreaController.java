package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import backlog_manager.exceptions.ProjectAreaInUseException;
import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;
import br.org.tutty.collaborative_whiteboard.cw.service.ProjectService;
import br.org.tutty.collaborative_whiteboard.cw_web.dtos.ProjectAreaCreation;
import cw.entities.Project;
import cw.entities.ProjectArea;
import cw.exceptions.EmptyEntityException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * Created by drferreira on 19/05/15.
 */
@Named
@ViewScoped
public class ProjectAreaController extends GenericController implements Serializable {
    @Inject
    private SessionContext sessionContext;
    @Inject
    private ProjectService projectService;

    private ProjectAreaCreation projectAreaCreation;

    @PostConstruct
    public void setUp() throws CloneNotSupportedException {
        Project selectedProject = sessionContext.getSelectedProject();
        projectAreaCreation = new ProjectAreaCreation(selectedProject);
    }

    public List<ProjectArea> fetchProjectAreas() {
        return projectService.fetchProjectAreas();
    }

    public ProjectAreaCreation getProjectAreaCreation() {
        return projectAreaCreation;
    }

    public void setProjectAreaCreation(ProjectAreaCreation projectAreaCreation) {
        this.projectAreaCreation = projectAreaCreation;
    }

    public void addProjectArea() throws IOException {
        try {
            ProjectArea projectArea = (ProjectArea) projectAreaCreation.toEntity();

            Boolean projectAreaAlreadyAdd = projectService.areaAlreadyAdded(projectArea.getProject(), projectArea.getName());
            Boolean projectAreaPrefixAlreadyAdd = projectService.prefixAreaAlreadyAdded(projectArea.getPrefix());

            if (projectAreaAlreadyAdd) {
                facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_WARN, "project.add.exist_area");

            } else if (projectAreaPrefixAlreadyAdd){
                facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_WARN, "project.add.exist_area_prefix");

            }else {
                projectService.createProjectArea(projectArea);
                facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_INFO, "project.add.area");
            }
        } catch (EmptyEntityException e) {}
    }

    public void removeProjectArea(ProjectArea projectArea) throws IOException {
        try {
            projectService.removeProjectAreas(projectArea);
            facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_INFO, "project.add.area.to_removal");
        } catch (ProjectAreaInUseException e) {
            facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_ERROR, "project.area.assigned_to_story", "project.area.remove.in_use");
        }
    }
}
