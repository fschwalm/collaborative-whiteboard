package br.org.tutty.collaborative_whiteboard.cw_web.dtos;

import cw.entities.User;
import org.primefaces.model.UploadedFile;

import java.io.Serializable;

/**
 * Created by drferreira on 25/05/15.
 */
public class ProfileEdition implements Serializable {

    private String firstUserName;
    private String lastUserName;
    private String email;
    private String password;
    private UploadedFile profilePicture;


    public void init(User user){
        if(user != null){
            this.firstUserName = user.getFirstName();
            this.lastUserName = user.getLastName();
            this.email = user.getEmail();
            this.password = user.getPassword();
//            this.birthdate = user.
        }
    }

    public String getFirstUserName() {
        return firstUserName;
    }

    public void setFirstUserName(String firstUserName) {
        this.firstUserName = firstUserName;
    }

    public String getLastUserName() {
        return lastUserName;
    }

    public void setLastUserName(String lastUserName) {
        this.lastUserName = lastUserName;
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
