package br.org.tutty.collaborative_whiteboard.transmition.model;

/**
 * Created by drferreira on 16/12/14.
 */
public class UserMessage extends Message {
    private String message;
    private String userName;

    public UserMessage(String userName, String message) {
        super(TypeMessage.USER_MESSAGE);
        this.userName = userName;
        this.message = message;
    }

    public void aditionalWrite(){
        write("MESSAGE", message);
        write("USERNAME", userName);
    }

}
