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
    private static final String DEFAULT_RESOURCE_NAME = "/properties/messages_pt_BR.properties";
    private String resourceFullName;

    public void setResourceName(String resourceFullName){
        this.resourceFullName = resourceFullName;
    }

    public void showGlobalMessage(FacesMessage.Severity severity, String keyPropertyMessage, String keyPropertyMessageDetail){
        try{
        String message = readPropertyMessage(keyPropertyMessage);
        String messageDetail = readPropertyMessage(keyPropertyMessageDetail);

        showMessage(null, severity, message, messageDetail);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public void showGlobalMessage(FacesMessage.Severity severity, String keyPropertyMessage){
        try{
            String message = readPropertyMessage(keyPropertyMessage);
            showMessage(null, severity, message, null);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public void showMessage(String target, FacesMessage.Severity severity, String message, String messageDetail) throws IOException {
        FacesContext.getCurrentInstance().addMessage(target, new FacesMessage(severity, message, messageDetail));
    }

    public FacesMessage getFacesMessage(FacesMessage.Severity severity, String keyPropertyMessage) throws IOException {
        return getFacesMessage(severity, keyPropertyMessage, null);
    }

    public FacesMessage getFacesMessage(FacesMessage.Severity severity, String keyPropertyMessage, String keyPropertyMessageDetail) throws IOException {
        String message = readPropertyMessage(keyPropertyMessage);
        String messageDetail = readPropertyMessage(keyPropertyMessageDetail);
        FacesMessage facesMessage = new FacesMessage(severity, message, messageDetail);

        setResourceNameToDefault();

        return facesMessage;
    }

    public String readPropertyMessage(String keyPropertie) throws IOException {
        InputStream resourceStream = getResourceStream();

        Properties properties = new Properties();
        properties.load(resourceStream);

        String property = properties.getProperty(keyPropertie);

        return property;
    }

    private void setResourceNameToDefault(){
        resourceFullName = null;
    }

    private InputStream getResourceStream(){
        if(resourceFullName != null){
            return this.getClass().getClassLoader().getResourceAsStream(resourceFullName);
        }else {
            return this.getClass().getClassLoader().getResourceAsStream(DEFAULT_RESOURCE_NAME);
        }
    }
}
