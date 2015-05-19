package br.org.tutty.collaborative_whiteboard.cw_web.dtos;

import br.org.tutty.util.PropertyMonitor;
import cw.entities.Project;
import cw.entities.ProjectArea;
import cw.exceptions.EmptyEntityException;

/**
 * Created by drferreira on 08/04/15.
 */
public class ProjectAreaCreation {

    private String projectAreaName;
    private String projectAreaAbbreviation;

    public PropertyMonitor propertyMonitor = new PropertyMonitor(this);
    private Project selectedProject;

    public ProjectAreaCreation(Project selectedProject) {
        this.selectedProject = selectedProject;
    }

    public Object toEntity() throws EmptyEntityException {
        if(propertyMonitor.hasChanged()){
            ProjectArea projectArea = new ProjectArea(projectAreaName, projectAreaAbbreviation);
            projectArea.setProject(selectedProject);

            projectAreaName = null;
            projectAreaAbbreviation = null;

            return projectArea;
        }

        throw new EmptyEntityException();
    }

    public void setProjectAreaName(String projectAreaName) {
        String oldValue = this.projectAreaName;
        this.projectAreaName = projectAreaName;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("projectAreaName", oldValue, projectAreaName);
    }

    public String getProjectAreaName() {
        return projectAreaName;
    }

    public void setProjectAreaAbbreviation(String projectAreaAbbreviation) {
        String oldValue = this.projectAreaAbbreviation;
        this.projectAreaAbbreviation = projectAreaAbbreviation;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("projectAreaAbbreviation", oldValue, projectAreaAbbreviation);
    }

    public String getProjectAreaAbbreviation() {
        return projectAreaAbbreviation;
    }
}
