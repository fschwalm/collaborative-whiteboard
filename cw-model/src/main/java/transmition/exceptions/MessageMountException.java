package transmition.exceptions;

/**
 * Created by drferreira on 12/12/14.
 */
public class MessageMountException extends Exception {
    public MessageMountException(Exception exception) {
        super(exception);
    }
}
