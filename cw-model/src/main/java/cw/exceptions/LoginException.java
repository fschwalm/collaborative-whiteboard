package cw.exceptions;

/**
 * Created by drferreira on 16/12/14.
 */
public class LoginException extends Exception {
    public LoginException(Exception cause) {
        super(cause);
    }
}
