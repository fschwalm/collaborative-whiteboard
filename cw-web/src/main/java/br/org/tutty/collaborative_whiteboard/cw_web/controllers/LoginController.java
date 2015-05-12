package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import br.org.tutty.collaborative_whiteboard.cw.service.SecurityService;
import cw.dtos.Security;
import cw.exceptions.LoginException;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

/**
 * Created by drferreira on 16/12/14.
 */
@Named
@ViewScoped
public class LoginController extends GenericController {

    @Inject
    private SecurityService securityService;

    private String email;
    private String password;

    private static String LOGIN_PROPERTY_KEY = "login.error";
    private static String FEATURE_NOT_IMPLEMENTED_PROPERTY_KEY = "feature.not_implemented";
    private static String UNEXPECTED_ERROR_PROPERTY_KEY = "unexpected.error";
    private static String UNEXPECTED_ERROR_DETAIL_PROPERTY_KEY = "unexpected.error.detail";

    public String login() throws IOException {
        try {
            Security security = new Security(getSession(), email, password);
            securityService.login(security);
            return HOME_PAGE;

        } catch (LoginException e) {
            facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_ERROR, LOGIN_PROPERTY_KEY );
            return RECOVERY_PAGE;

        }catch (Exception exception){
            facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_FATAL, UNEXPECTED_ERROR_PROPERTY_KEY, UNEXPECTED_ERROR_DETAIL_PROPERTY_KEY);
            exception.printStackTrace();
            return RECOVERY_PAGE;
        }
    }

    public String recovery() throws IOException {
        facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_ERROR, FEATURE_NOT_IMPLEMENTED_PROPERTY_KEY);
        return RECOVERY_PAGE;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
