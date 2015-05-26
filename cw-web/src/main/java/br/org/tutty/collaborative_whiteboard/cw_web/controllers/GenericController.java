package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import br.org.tutty.collaborative_whiteboard.cw_web.utils.FacesMessageUtil;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

/**
 * Created by drferreira on 12/12/14.
 */
public class GenericController implements Serializable {

    protected static final String STAY_ON_PAGE = "";
    protected static final String HOME_PAGE = "HOME_PAGE";
    protected static final String CREATE_ACCOUNT_PAGE = "ACCOUNT_CREATION_PAGE";
    protected static final String RECOVERY_PAGE = "";
    protected static final String GO_TO_BACKLOG = "BACKLOG_PAGE";
    protected static final String WELCOME_PAGE = "WELCOME_PAGE";
    protected static final String GOT_TO_PROJECT_PAGE = "PROJECT_PAGE";
    protected static final String GOT_TO_VOTING_PAGE = "VOTING_PAGE";


    @Inject
    protected FacesMessageUtil facesMessageUtil;

    @Inject
    private HttpSession httpSession;

    public HttpSession getSession(){
        System.out.print(httpSession.getId());
        return httpSession;
    }
}
