package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import br.org.tutty.collaborative_whiteboard.cw.handlers.WhiteboardHandler;
import br.org.tutty.collaborative_whiteboard.cw.service.WhiteboardService;
import cw.entities.Stage;
import cw.exceptions.DataNotFoundException;

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

    private String stageNameForCreation;

    public void createStage() throws DataNotFoundException {
        Stage stage = new Stage(stageNameForCreation);
        whiteboardService.createStage(stage);
        stageNameForCreation = null;
    }

    public String vote(){
        return GOT_TO_TASKS_PAGE;
    }

    public void cancel(){
        stageNameForCreation = null;
    }

    public void drawMyWhiteboard(){
        whiteboardService.refreshWhiteboard(whiteboardHandler.getLastConnect());
    }

    public String getStageNameForCreation() {
        return stageNameForCreation;
    }

    public void setStageNameForCreation(String name) {
        this.stageNameForCreation = name;
    }
}
