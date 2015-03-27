package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

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

    @Inject
    private HttpSession httpSession;

    public HttpSession getSession(){
        System.out.print(httpSession.getId());
        return httpSession;
    }

    public void showGlobalMessage(FacesMessage.Severity severity, String keyPropertyMessage, String keyPropertyMessageDetail) throws IOException {
        String message = readPropertyMessage(keyPropertyMessage);
        String messageDetail = readPropertyMessage(keyPropertyMessageDetail);

        showMessage(null, severity, message, messageDetail);
    }

    public void showGlobalMessageWithoutDetail(FacesMessage.Severity severity, String keyPropertyMessage) throws IOException {
        String message = readPropertyMessage(keyPropertyMessage);
        showMessage(null, severity, message, null);
    }

    public void showMessage(String target, FacesMessage.Severity severity, String message, String messageDetail) throws IOException {
        FacesContext.getCurrentInstance().addMessage(target, new FacesMessage(severity, message, messageDetail));
    }

    private String readPropertyMessage(String keyPropertie) throws IOException {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("/properties/messages.properties");
        Properties properties = new Properties();
        properties.load(resourceAsStream);

        String property = properties.getProperty(keyPropertie);
        resourceAsStream.close();

        return property;
    }

}
