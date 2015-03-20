package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import br.org.tutty.collaborative_whiteboard.cw.service.UserService;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by drferreira on 15/03/15.
 */
@Named
public class WhiteboardController extends GenericController implements Serializable {

    @Inject
    private UserService userService;

    public String createProject(){
        // TODO
        return GO_TO_CREATE_PROJECT;
    }

    public String goToBacklog(){
        return GO_TO_BACKLOG;
    }

    public Boolean hasSomeProject(){
        return userService.hasSomeProject();
    }
}
