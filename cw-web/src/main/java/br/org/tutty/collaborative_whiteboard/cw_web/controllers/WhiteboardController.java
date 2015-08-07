package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import br.org.tutty.collaborative_whiteboard.backlog_manager.services.TaskManagerService;
import br.org.tutty.collaborative_whiteboard.cw.handlers.WhiteboardHandler;
import br.org.tutty.collaborative_whiteboard.cw.service.WhiteboardService;
import cw.entities.Stage;
import cw.exceptions.DataNotFoundException;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by drferreira on 20/05/15.
 */
@ViewScoped
@Named
public class WhiteboardController extends GenericController implements Serializable {
    @Inject
    private WhiteboardService whiteboardService;

    @Inject
    private WhiteboardHandler whiteboardHandler;

    @Inject
    private TaskManagerService taskManagerService;

    private String stageNameForCreation;

    public void createStage() throws DataNotFoundException {
        Stage stage = new Stage(stageNameForCreation);
        whiteboardService.createStage(stage);
        stageNameForCreation = null;
        facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_INFO, "whiteboard.stage.created.sucess", "whiteboard.stage.created.detail");
    }

    public void taskNext(){
        facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_ERROR, "feature.not_implemented", "feature.not_implemented.detail");
    }

    public void taskPlay(){
        facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_ERROR, "feature.not_implemented", "feature.not_implemented.detail");
    }

    public void taskStop(){
        facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_ERROR, "feature.not_implemented", "feature.not_implemented.detail");
    }

    public void taskPrev(){
        facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_ERROR, "feature.not_implemented", "feature.not_implemented.detail");
    }

    public void cancel(){
        stageNameForCreation = null;
    }

    public String getStageNameForCreation() {
        return stageNameForCreation;
    }

    public void setStageNameForCreation(String name) {
        this.stageNameForCreation = name;
    }
}
