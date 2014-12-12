package br.org.tutty.collaborative_whiteboard.transmition;

import br.org.tutty.collaborative_whiteboard.model.User;
import br.org.tutty.collaborative_whiteboard.transmition.exceptions.MessageMountException;
import org.json.JSONObject;

/**
 * Created by drferreira on 12/12/14.
 */
public class ChatMessageBuilder implements MessageBuilder {

    private String message;
    private User senderUser;

    public ChatMessageBuilder(String message, User senderUser) {
        this.message = message;
        this.senderUser = senderUser;
    }

    @Override
    public JSONObject getMessage() throws MessageMountException {
        String senderName = senderUser.getName();

        try{
            JSONObject jsonObject = new JSONObject(message);
            jsonObject.putOpt("user", senderName);

            return jsonObject;
        }catch (Exception exception){
            throw new MessageMountException(exception);
        }
    }
}
