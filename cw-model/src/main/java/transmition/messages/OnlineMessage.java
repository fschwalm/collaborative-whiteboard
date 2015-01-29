package transmition.messages;

/**
 * Created by drferreira on 16/12/14.
 */
public class OnlineMessage extends StatusMessage {
    private static final String STATUS = "Online";

    public OnlineMessage() {
        super(STATUS);
    }
}
