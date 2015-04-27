package br.org.tutty.collaborative_whiteboard.cw_web.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

/**
 * Created by drferreira on 27/04/15.
 */
public class FacesMessageUtil implements Serializable{

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

    public FacesMessage getFacesMessage(FacesMessage.Severity severity, String keyPropertyMessage) throws IOException {
        String message = readPropertyMessage(keyPropertyMessage);
        return new FacesMessage(severity, message, null);
    }

    public String readPropertyMessage(String keyPropertie) throws IOException {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("/properties/messages.properties");
        Properties properties = new Properties();
        properties.load(resourceAsStream);

        String property = properties.getProperty(keyPropertie);
        resourceAsStream.close();

        return property;
    }
}
