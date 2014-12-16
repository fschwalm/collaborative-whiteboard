package br.org.tutty.collaborative_whiteboard.cw.model;

import br.org.tutty.collaborative_whiteboard.cw.exceptions.EncryptedException;
import br.org.tutty.collaborative_whiteboard.cw.utils.EncryptorResources;
import javax.servlet.http.HttpSession;

/**
 * Created by drferreira on 16/12/14.
 */
public class Security {
    private final HttpSession httpSession;
    private final String email;
    private final String password;

    public Security(HttpSession httpSession, String email, String password) throws EncryptedException {
        this.httpSession = httpSession;
        this.email = email;
        this.password = EncryptorResources.encrypt(password);
    }

    public HttpSession getHttpSession() {
        return httpSession;
    }

    public String getEmail() {
        return email;
    }

    public Boolean checkPassword(String password) {
        return this.password.equals(password);
    }

}
