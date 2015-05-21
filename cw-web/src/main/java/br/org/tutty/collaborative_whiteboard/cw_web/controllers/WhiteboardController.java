package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

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

    private String name;


    public void createStage() throws DataNotFoundException {
        Stage stage = new Stage(name);
        whiteboardService.createStage(stage);
        name = null;
    }

    public void removeStage(){

    }

    public void reorderStage(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
