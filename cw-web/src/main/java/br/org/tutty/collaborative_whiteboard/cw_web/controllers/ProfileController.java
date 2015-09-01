package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;
import br.org.tutty.collaborative_whiteboard.cw.service.UserService;
import br.org.tutty.collaborative_whiteboard.cw_web.dtos.UserEdition;
import cw.dtos.LoggedUser;
import cw.entities.User;
import cw.exceptions.DataNotFoundException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by drferreira on 28/08/15.
 */
@Named
@SessionScoped
public class ProfileController extends GenericController implements Serializable {
    private static String INVALID_TYPE_KEY = "invalid.type.picture";
    private static String INVALID_TYPE_KEY_DETAIL = "invalid.type.picture.detail";
    private static String TYPE_FILE_JPG = "jpg";
    private static String TYPE_FILE_PNG = "png";

    @Inject
    private SessionContext sessionContext;

    @Inject
    private UserEdition userEdition;

    @Inject
    private UserService userService;

    @PostConstruct
    public void setUp() throws DataNotFoundException {
        LoggedUser loggedUser = sessionContext.getLoggedUser();
        userEdition.init(loggedUser.getUser());
    }

    public void save(){
        User changedUser = userEdition.toEntity();
        userService.update(changedUser);
        facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_WARN, "project.add.exist_area");
    }

    public UserEdition getUserEdition() {
        return userEdition;
    }

    public void setUserEdition(UserEdition userEdition) {
        this.userEdition = userEdition;
    }

    public StreamedContent getPicture() throws IOException {
        return userEdition.getProfilePicture();
    }

    public void updatePicture(FileUploadEvent fileUploadEvent) {
        UploadedFile file = fileUploadEvent.getFile();

        if (isValidPictureType(file)) {
            userEdition.setProfilePicture(file);

        } else {
            facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_ERROR, INVALID_TYPE_KEY, INVALID_TYPE_KEY_DETAIL);
        }
    }

    private Boolean isValidPictureType(UploadedFile uploadedFile) {
        String contentType = uploadedFile.getContentType();
        return contentType.contains(TYPE_FILE_JPG) || contentType.contains(TYPE_FILE_PNG);
    }
}
