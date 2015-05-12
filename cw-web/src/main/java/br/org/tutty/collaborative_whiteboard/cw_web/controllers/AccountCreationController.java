package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import br.org.tutty.collaborative_whiteboard.cw.service.SecurityService;
import br.org.tutty.collaborative_whiteboard.cw.service.UserService;
import cw.dtos.EncryptorResources;
import cw.dtos.Security;
import cw.entities.User;
import cw.exceptions.DataNotFoundException;
import cw.exceptions.EncryptedException;
import cw.exceptions.LoginException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by root on 16/03/15.
 */
@Named
@ViewScoped
public class AccountCreationController extends GenericController implements Serializable {

    @Inject
    private UserService userService;

    @Inject
    private SecurityService securityService;

    private String email;
    private String firstName;
    private String lastName;
    private Date birthdate;
    private String password;

    private Date birthdateMax;

    @PostConstruct
    public void setUp(){
        initCalendarValidation();
    }

    private void initCalendarValidation() {
        birthdateMax = new Date();
    }

    public String create() throws EncryptedException, LoginException, IOException {
        if(!isAlreadyRegistered(email)){
            User user = new User(email, password, firstName, lastName);
            userService.create(user);

            authenticateUser();
            return HOME_PAGE;
        }else {
            facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_WARN, "create_account.already_registered");
            return STAY_ON_PAGE;
        }
    }

    private void authenticateUser() throws EncryptedException, LoginException {
        Security security = new Security(getSession(), email, password);
        securityService.login(security);
    }

    private Boolean isAlreadyRegistered(String email){
        try {
            userService.fetch(email);
            return Boolean.TRUE;

        } catch (DataNotFoundException e) {
            return Boolean.FALSE;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date birthdateMax() {
        return birthdateMax;
    }
}
