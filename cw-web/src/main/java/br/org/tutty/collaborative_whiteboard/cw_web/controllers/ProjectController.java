package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import backlog_manager.exceptions.ProjectAreaInUseException;
import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;
import br.org.tutty.collaborative_whiteboard.cw.service.ProjectService;
import br.org.tutty.collaborative_whiteboard.cw_web.dtos.ProjectAreaCreation;
import com.sun.org.apache.xpath.internal.operations.Bool;
import cw.entities.Project;
import cw.entities.ProjectArea;
import cw.exceptions.DataNotFoundException;

import javax.annotation.PostConstruct;
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by drferreira on 11/03/15.
 */
@Named
@ViewScoped
public class ProjectController extends GenericController implements Serializable {

    @Inject
    private SessionContext sessionContext;

    @Inject
    private ProjectService projectService;

    private Project selectedProject;

    private ProjectAreaCreation projectAreaCreation;

    private Set<ProjectArea> projectAreasForRemoval;


    @PostConstruct
    public void setUp() throws CloneNotSupportedException {
        selectedProject = sessionContext.getSelectedProject();
        projectAreaCreation = new ProjectAreaCreation(selectedProject, projectService.fetchProjectAreas());
        projectAreasForRemoval = new HashSet<>();
    }

    public String save() throws IOException {
        if (selectedProject.propertyMonitor.hasChanged()) {
            updateProject();
        }

        if (projectAreaCreation.propertyMonitor.hasChanged()) {
            updateProjectArea();
        }

        if (!projectAreasForRemoval.isEmpty()) {
            updateProjectAreaForRemoval();
        }

        showGlobalMessageWithoutDetail(FacesMessage.SEVERITY_INFO, "project.update");
        return STAY_ON_PAGE;
    }

    public void removeProject() throws IOException {
        showGlobalMessageWithoutDetail(FacesMessage.SEVERITY_WARN, "feature.not_implemented");
    }

    private void updateProject() throws IOException {
        projectService.update(selectedProject);
    }

    private void updateProjectArea() throws IOException {
        projectAreaCreation.getProjectAreas().removeIf(projectArea -> projectAreasForRemoval.contains(projectArea));
        projectService.createProjectArea(projectAreaCreation.getProjectAreas());
    }

    private void updateProjectAreaForRemoval() throws IOException {
        try{
            projectService.removeProjectAreas(projectAreasForRemoval);

        }catch (EJBTransactionRolledbackException e){
            showGlobalMessageWithoutDetail(FacesMessage.SEVERITY_ERROR, "project.area.remove.in_use");
        }

    }

    public void addProjectArea() throws IOException {
        try {
            projectService.fetchProjectArea(selectedProject, projectAreaCreation.getProjectAreaName());
            showGlobalMessageWithoutDetail(FacesMessage.SEVERITY_WARN, "project.add.exist_area");
        } catch (DataNotFoundException e) {
            projectAreaCreation.addArea();
            showGlobalMessageWithoutDetail(FacesMessage.SEVERITY_WARN, "project.add.area");
        }
    }

    public List<ProjectArea> fetchProjectAreas() {
        return projectAreaCreation.getProjectAreas();
    }

    public boolean isCheckForRemoval(String projectAreaName) {
        return projectAreasForRemoval.stream().anyMatch(area -> area.getName().equals(projectAreaName));
    }

    public String discard() {
        return HOME_PAGE;
    }

    public String getOwnerName() {
        return selectedProject.getOwner().getFullName();
    }

    public String getProjectName() {
        return selectedProject.getNameProject();
    }

    public String getCreationDate() {
        return selectedProject.getCreationDate().toString();
    }

    public String getProjectDescription() {
        return selectedProject.getDescription();
    }

    public String getProjectPrefix() {
        return selectedProject.getPrefix();
    }

    public String getColor() {
        return selectedProject.getColor();
    }

    public void setProjectDescription(String description) {
        selectedProject.setDescription(description);
    }

    public void setProjectPrefix(String projectPrefix) {
        selectedProject.setPrefix(projectPrefix);
    }

    public void setColor(String color) {
        selectedProject.setColor(color);
    }

    public void setProjectName(String projectName) {
        selectedProject.setNameProject(projectName);
    }

    public String getProjectAreaName() {
        return projectAreaCreation.getProjectAreaName();
    }

    public void setProjectAreaName(String projectAreaName) {
        this.projectAreaCreation.setProjectAreaName(projectAreaName);
    }

    public void removeArea(ProjectArea projectArea) throws IOException {
        if (isCheckForRemoval(projectArea.getName())) {
            projectAreasForRemoval.remove(projectArea);
            showGlobalMessageWithoutDetail(FacesMessage.SEVERITY_WARN, "project.exclude.area.to_removal");
        } else {
            projectAreasForRemoval.add(projectArea);
            showGlobalMessageWithoutDetail(FacesMessage.SEVERITY_WARN, "project.add.area.to_removal");
        }

    }

}
