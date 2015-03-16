package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;
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

    private static final String GO_TO_BACKLOG = "BACKLOG_PAGE";

    public String goToBacklog(){
        return GO_TO_BACKLOG;
    }

    public Boolean hasSomeProject(){
        return userService.hasSomeProject();
    }





}
