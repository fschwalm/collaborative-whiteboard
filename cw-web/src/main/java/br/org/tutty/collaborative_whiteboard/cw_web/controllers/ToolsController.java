package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by drferreira on 30/04/15.
 */
@Named
@ViewScoped
public class ToolsController extends GenericController implements Serializable{

    public String goToBacklog(){
        return GO_TO_BACKLOG;
    }
}
