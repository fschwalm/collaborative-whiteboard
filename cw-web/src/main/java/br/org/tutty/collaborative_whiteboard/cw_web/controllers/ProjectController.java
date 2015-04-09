package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;
import br.org.tutty.collaborative_whiteboard.cw.service.ProjectService;
import br.org.tutty.collaborative_whiteboard.cw_web.dtos.ProjectAreaCreation;
import cw.entities.Project;
import cw.entities.ProjectArea;

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

    private List<ProjectArea> projectAreasForRemoval;


    @PostConstruct
    public void setUp() throws CloneNotSupportedException {
        selectedProject = sessionContext.getSelectedProject();
        projectAreaCreation = new ProjectAreaCreation(selectedProject, projectService.fetchProjectAreas());
        projectAreasForRemoval = new ArrayList<>();
    }

    public String save() throws IOException {
        if(selectedProject.propertyMonitor.hasChanged()) {
            updateProject();
        }

        if (projectAreaCreation.propertyMonitor.hasChanged()){
            updateProjectArea();
        }

        showGlobalMessageWithoutDetail(FacesMessage.SEVERITY_INFO, "project.update");
        return STAY_ON_PAGE;
    }

    public Boolean hasAreaForRemoval(){
        return !(projectAreasForRemoval != null && projectAreasForRemoval.isEmpty());
    }

    private void updateProject() throws IOException {
        projectService.update(selectedProject);
    }

    private void updateProjectArea() throws IOException {
        projectService.createProjectArea(projectAreaCreation.getProjectAreas());
    }

    public void addProjectArea() throws IOException {
        projectAreaCreation.addArea();
        showGlobalMessageWithoutDetail(FacesMessage.SEVERITY_WARN, "project.add.area");
    }

    public List<ProjectArea> fetchProjectAreas(){
        return projectAreaCreation.getProjectAreas();
    }

    public String discard(){
        return HOME_PAGE;
    }

    public String getOwnerName(){
        return selectedProject.getOwner().getFullName();
    }

    public String getProjectName(){
        return selectedProject.getNameProject();
    }

    public String getCreationDate(){
        return selectedProject.getCreationDate().toString();
    }

    public String getProjectDescription(){
        return selectedProject.getDescription();
    }

    public String getProjectPrefix(){
        return selectedProject.getPrefix();
    }

    public String getColor(){
        return selectedProject.getColor();
    }

    public void setProjectDescription(String description){
        selectedProject.setDescription(description);
    }

    public void setProjectPrefix(String projectPrefix){
        selectedProject.setPrefix(projectPrefix);
    }

    public void setColor(String color){
        selectedProject.setColor(color);
    }

    public void setProjectName(String projectName){
        selectedProject.setNameProject(projectName);
    }

    public String getProjectAreaName() {
        return projectAreaCreation.getProjectAreaName();
    }

    public void setProjectAreaName(String projectAreaName) {
        this.projectAreaCreation.setProjectAreaName(projectAreaName);
    }

    public void setSelectedAreasForRemoval(List<ProjectArea> projectAreasForRemoval){
        this.projectAreasForRemoval = projectAreasForRemoval;
    }

    public List<ProjectArea> getSelectedAreasForRemoval(){
        return projectAreasForRemoval;
    }

}
