package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import br.org.tutty.collaborative_whiteboard.cw.exceptions.EncryptedException;
import br.org.tutty.collaborative_whiteboard.cw.exceptions.LoginException;
import br.org.tutty.collaborative_whiteboard.cw.model.Security;
import br.org.tutty.collaborative_whiteboard.cw.service.SecurityService;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

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

    public String login(){
        try {
            Security security = new Security(getSession(), email, password);
            securityService.login(security);
            return HOME_PAGE;

        } catch (EncryptedException e) {
            e.printStackTrace();
            return "";
        } catch (LoginException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String createAccount(){
        return CREATE_ACCOUNT_PAGE;
    }

    public String recovery(){
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
