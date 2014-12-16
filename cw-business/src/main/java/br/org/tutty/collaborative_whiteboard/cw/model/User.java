package br.org.tutty.collaborative_whiteboard.cw.model;

import br.org.tutty.collaborative_whiteboard.cw.exceptions.EncryptedException;
import br.org.tutty.collaborative_whiteboard.cw.utils.EncryptorResources;

/**
 * Created by drferreira on 12/12/14.
 */
public class User {

    private String email;
    private String password;
    private String name;

    public User(String email, String password, String name) throws EncryptedException {
        this.email = email;
        this.password = EncryptorResources.encrypt(password);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
