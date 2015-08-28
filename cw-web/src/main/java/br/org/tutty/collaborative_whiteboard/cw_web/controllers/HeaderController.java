package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by drferreira on 28/08/15.
 */
@Named
@ViewScoped
public class HeaderController extends GenericController implements Serializable {

    @Inject
    private SessionContext sessionContext;

    public String getUserName(){
        return sessionContext.getLoggedUser().getUser().getFirstName();
    }
}
