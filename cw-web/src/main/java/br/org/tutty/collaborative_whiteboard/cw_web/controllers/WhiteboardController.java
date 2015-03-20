package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;
import br.org.tutty.collaborative_whiteboard.cw.service.ProjectService;
import br.org.tutty.collaborative_whiteboard.cw.service.UserService;
import cw.exceptions.NameInUseException;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

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
    private UserService userService;

    @Inject
    private ProjectService projectService;

    public String createProject() throws IOException {
        try {
            projectService.createProject(projectName);
            showGlobalMessageWithoutDetail(FacesMessage.SEVERITY_INFO, "whiteboard.project_created");

            return GO_TO_CREATE_PROJECT;

        } catch (NameInUseException e) {
            showGlobalMessageWithoutDetail(FacesMessage.SEVERITY_WARN, "whiteboard.name_in_use");
            return STAY_ON_PAGE;
        }
    }

    public String goToBacklog(){
        return GO_TO_BACKLOG;
    }

    public Boolean hasSomeProject(){
        return userService.hasSomeProject();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
