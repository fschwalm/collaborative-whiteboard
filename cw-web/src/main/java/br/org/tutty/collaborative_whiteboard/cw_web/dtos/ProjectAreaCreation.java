package br.org.tutty.collaborative_whiteboard.cw_web.dtos;

import br.org.tutty.util.PropertyMonitor;
import cw.entities.Project;
import cw.entities.ProjectArea;

import java.util.List;

/**
 * Created by drferreira on 08/04/15.
 */
public class ProjectAreaCreation {

    private Project selectedProject;
    private List<ProjectArea> projectAreas;
    private String projectAreaName;
    private String projectAreaAbbreviation;

    public PropertyMonitor propertyMonitor = new PropertyMonitor(this);

    public ProjectAreaCreation(Project selectedProject, List<ProjectArea> projectAreas) {
        this.selectedProject = selectedProject;
        this.projectAreas = projectAreas;
    }

    public void addArea(){
        ProjectArea projectArea = new ProjectArea(projectAreaName, projectAreaAbbreviation);
        projectArea.setProject(selectedProject);
        projectAreas.add(projectArea);

        projectAreaName = null;
        projectAreaAbbreviation = null;
    }

    public List<ProjectArea> getProjectAreas() {
        return projectAreas;
    }

    public String getProjectAreaName() {
        return projectAreaName;
    }

    public String getProjectAreaAbbreviation() {
        return projectAreaAbbreviation;
    }

    public void setProjectAreaName(String projectAreaName) {
        String oldValue = this.projectAreaName;
        this.projectAreaName = projectAreaName;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("projectAreaName", oldValue, projectAreaName);
    }

    public void setProjectAreaAbbreviation(String projectAreaAbbreviation) {
        String oldValue = this.projectAreaAbbreviation;
        this.projectAreaAbbreviation = projectAreaAbbreviation;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("projectAreaAbbreviation", oldValue, projectAreaAbbreviation);
    }

    public Boolean alreadyAdded(String projectAreaName){
        return getProjectAreas().stream().anyMatch(project -> project.getName().equals(projectAreaName));
    }

}
