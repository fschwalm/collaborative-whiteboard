package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by drferreira on 15/03/15.
 */
@Named
public class WhiteboardController extends GenericController implements Serializable {

    private static final String GO_TO_BACKLOG = "BACKLOG_PAGE";

    public String goToBacklog(){
        return GO_TO_BACKLOG;
    }

}
