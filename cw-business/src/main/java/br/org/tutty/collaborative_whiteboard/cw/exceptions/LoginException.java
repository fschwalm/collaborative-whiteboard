package br.org.tutty.collaborative_whiteboard.cw.exceptions;

/**
 * Created by drferreira on 16/12/14.
 */
public class LoginException extends Exception {
    private Exception cause;

    public LoginException(Exception cause) {
        this.cause = cause;
    }
}
