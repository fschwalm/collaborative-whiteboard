package cw.exceptions;

/**
 * Created by drferreira on 16/12/14.
 */
public class AuthenticationException extends Exception {

    public AuthenticationException() {
    }

    public AuthenticationException(Exception cause) {
        super(cause);
    }
}
