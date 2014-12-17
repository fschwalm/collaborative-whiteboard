package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * Created by drferreira on 12/12/14.
 */
public class GenericController implements Serializable {

    protected static final String HOME_PAGE = "HOME_PAGE";
    protected static final String CREATE_ACCOUNT_PAGE = "";
    protected static final String RECOVERY_PAGE = "";

    @Inject
    private HttpSession httpSession;

    public HttpSession getSession(){
        System.out.print(httpSession.getId());
        return httpSession;
    }
}
