package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;
import br.org.tutty.collaborative_whiteboard.cw_web.dtos.ProfileEdition;
import cw.dtos.LoggedUser;
import cw.entities.User;
import org.primefaces.component.fileupload.FileUpload;
import org.primefaces.event.FileUploadEvent;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * Created by drferreira on 28/08/15.
 */
@Named
@ViewScoped
public class ProfileController extends GenericController implements Serializable {

    @Inject
    private SessionContext sessionContext;

    @Inject
    private ProfileEdition profileEdition;

    @PostConstruct
    public void setUp(){
        LoggedUser loggedUser = sessionContext.getLoggedUser();
        profileEdition.init(loggedUser.getUser());
    }

    public ProfileEdition getProfileEdition() {
        return profileEdition;
    }

    public void setProfileEdition(ProfileEdition profileEdition) {
        this.profileEdition = profileEdition;
    }

    public String getPictureUrl(){
        return "../../images/user-male.png";
    }
}
