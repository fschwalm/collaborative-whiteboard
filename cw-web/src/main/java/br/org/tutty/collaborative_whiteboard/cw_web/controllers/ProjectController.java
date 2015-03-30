package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;
import cw.entities.Project;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by drferreira on 11/03/15.
 */
@Named
@ViewScoped
public class ProjectController extends GenericController implements Serializable {

    @Inject
    private SessionContext sessionContext;

    private Project selectedProject;

    @PostConstruct
    public void setUp(){
        selectedProject = sessionContext.getSelectedProject();
    }

    public void save(){
    }

    public Boolean hasChanged(){
        return selectedProject.propertyMonitor.hasChanged();
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

}
