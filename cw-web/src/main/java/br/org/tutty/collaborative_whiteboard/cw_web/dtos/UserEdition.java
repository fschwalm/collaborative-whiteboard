package br.org.tutty.collaborative_whiteboard.cw_web.dtos;

import cw.entities.User;
import cw.exceptions.DataNotFoundException;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

import javax.persistence.*;
import br.org.tutty.util.PropertyMonitor;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by drferreira on 25/05/15.
 */
public class UserEdition implements Serializable {

    public User user;
    private String firstUserName;
    private String lastUserName;
    private String email;
    private byte[] profilePicture;
    private Date birthdate;

    @Transient
    public PropertyMonitor propertyMonitor = new PropertyMonitor(this);

    public void init(User user) throws DataNotFoundException {
        if (user != null) {
            this.user = user;
            this.firstUserName = user.getFirstName();
            this.lastUserName = user.getLastName();
            this.email = user.getEmail();
            this.birthdate = user.getBirthdate();
            this.profilePicture = user.getProfilePicture();
        } else {
            throw new DataNotFoundException();
        }
    }

    public User toEntity(){
        this.user.setFirstName(firstUserName);
        this.user.setLastName(lastUserName);
        this.user.setEmail(email);
        this.user.setProfilePicture(profilePicture);

        return user;
    }

    public String getFirstUserName() {
        return firstUserName;
    }

    public void setFirstUserName(String firstUserName) {
	String oldValue = this.firstUserName;
        this.firstUserName = firstUserName;

        propertyMonitor.getPropertyChangeSupport().firePropertyChange("firstNmae", oldValue, firstUserName);
    }

    public String getLastUserName() {
        return lastUserName;
    }

    public void setLastUserName(String lastUserName) {
        String oldValue = this.lastUserName;
	    this.lastUserName = lastUserName;
        

	propertyMonitor.getPropertyChangeSupport().firePropertyChange("lastUserName", oldValue, lastUserName);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public DefaultStreamedContent getProfilePicture() {
        if(profilePicture != null){
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(profilePicture);
            return new DefaultStreamedContent(new BufferedInputStream(byteArrayInputStream));
        }else {
            return new DefaultStreamedContent();
        }
    }

    public void setProfilePicture(UploadedFile uploadedFile) {
        try {
            byte[] bytes = IOUtils.toByteArray(uploadedFile.getInputstream());
            this.profilePicture = bytes;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
