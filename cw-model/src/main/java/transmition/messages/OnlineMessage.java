package transmition.messages;

import cw.entities.User;
import org.json.JSONObject;
import transmition.entities.SentMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by drferreira on 16/12/14.
 */
public class OnlineMessage extends StatusMessage {
    private static final String STATUS = "Online";
    private static final String LAST_MESSAGES = "LAST_MESSAGES";

    private List<SentMessage> lastMessages;

    public OnlineMessage(List<SentMessage> lastMessages) {
        super(STATUS);
        this.lastMessages = lastMessages;
    }

    public void aditionalWrite(){
        List<JSONObject> userMessages = new ArrayList<>();

        for (SentMessage lastMessage : lastMessages){
            User sender = lastMessage.getSender();
            String messageData = lastMessage.getMessageData();

            UserMessage message = new UserMessage(sender.getFirstName(), messageData);
            userMessages.add(message.toJSon());
        }

        write(LAST_MESSAGES, userMessages);
    }
}
