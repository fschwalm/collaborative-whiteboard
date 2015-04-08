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

    public PropertyMonitor propertyMonitor = new PropertyMonitor(this);

    public ProjectAreaCreation(Project selectedProject, List<ProjectArea> projectAreas) {
        this.selectedProject = selectedProject;
        this.projectAreas = projectAreas;
    }

    public void addArea(){
        ProjectArea projectArea = new ProjectArea(projectAreaName);
        projectArea.setProject(selectedProject);
        projectAreas.add(projectArea);

        projectAreaName = null;
    }

    public List<ProjectArea> getProjectAreas() {
        return projectAreas;
    }

    public String getProjectAreaName() {
        return projectAreaName;
    }

    public void setProjectAreaName(String projectAreaName) {
        String oldValue = this.projectAreaName;
        this.projectAreaName = projectAreaName;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("projectAreaName", oldValue, projectAreaName);
    }
}
