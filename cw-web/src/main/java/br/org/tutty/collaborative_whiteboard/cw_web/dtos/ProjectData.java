package br.org.tutty.collaborative_whiteboard.cw_web.dtos;

import br.org.tutty.util.PropertyMonitor;
import cw.entities.Project;
import cw.exceptions.EmptyEntityException;

import java.util.Date;

/**
 * Created by drferreira on 19/05/15.
 */
public class ProjectData {

    private Project selectedProject;
    private String projectName;
    private String color;
    private String prefix;
    private String description;

    public PropertyMonitor propertyMonitor = new PropertyMonitor(this);

    public ProjectData(Project selectedProject) {
        this.selectedProject = selectedProject;

        this.projectName = selectedProject.getNameProject();
        this.color = selectedProject.getColor();
        this.prefix = selectedProject.getPrefix();
        this.description = selectedProject.getDescription();
    }

    public Date getCreationDate(){
        return selectedProject.getCreationDate();
    }

    public String getProjectName(){
        return projectName;
    }

    public void setProjectName(String projectName) {
        String oldValue = this.projectName;
        this.projectName = projectName;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("projectName", oldValue, projectName);
    }

    public String getOwnerName(){
        return selectedProject.getOwner().getFullName();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        String oldValue = this.color;
        this.color = color;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("color", oldValue, color);
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        String oldValue = this.prefix;
        this.prefix = prefix;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("prefix", oldValue, prefix);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        String oldValue = this.description;
        this.description = description;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("description", oldValue, description);
    }

    public Object toEntity() throws EmptyEntityException {
        if(propertyMonitor.hasChanged()){
            selectedProject.setNameProject(projectName);
            selectedProject.setColor(color);
            selectedProject.setPrefix(prefix);
            selectedProject.setDescription(description);

            projectName = null;
            color = null;
            prefix = null;
            description = null;

            return selectedProject;
        }

        throw new EmptyEntityException();
    }

}
