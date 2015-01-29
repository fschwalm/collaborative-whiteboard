package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * Created by drferreira on 12/12/14.
 */
public class GenericController implements Serializable {

    protected static final String HOME_PAGE = "HOME_PAGE";
    protected static final String CREATE_ACCOUNT_PAGE = "USER_CREATION_PAGE";
    protected static final String RECOVERY_PAGE = "";

    @Inject
    private HttpSession httpSession;

    public HttpSession getSession(){
        System.out.print(httpSession.getId());
        return httpSession;
    }

    public void showGlobalMessage(FacesMessage.Severity severity, String keyPropertyMessage, String keyPropertyMessageDetail){
        showMessage(null, severity, keyPropertyMessage, keyPropertyMessageDetail);
    }

    public void showGlobalMessageWithoutDetail(FacesMessage.Severity severity, String keyPropertyMessage){
        showMessage(null, severity, keyPropertyMessage, null);
    }

    public void showMessage(String target, FacesMessage.Severity severity, String keyPropertyMessage, String keyPropertyMessageDetail){
        FacesContext.getCurrentInstance().addMessage(target, new FacesMessage(severity, keyPropertyMessage, keyPropertyMessageDetail));
    }
}
