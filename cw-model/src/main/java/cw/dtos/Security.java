package cw.dtos;

import cw.entities.User;
import cw.exceptions.AuthenticationException;
import cw.exceptions.EncryptedException;

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

    public void checkAuthentication(User foundUser) throws AuthenticationException {
        if(! foundUser.getPassword().equals(password)){
            throw new AuthenticationException();
        }
    }
}
