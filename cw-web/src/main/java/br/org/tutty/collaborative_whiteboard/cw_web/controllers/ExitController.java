package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import br.org.tutty.collaborative_whiteboard.cw.service.SecurityService;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by drferreira on 16/12/14.
 */
@Named
@ViewScoped
public class ExitController extends GenericController {

    @Inject
    private SecurityService securityService;

    public String exit(){
        securityService.logout(getSession());
        getSession().invalidate();
        return WELCOME_PAGE;
    }
}
