package transmition.messages;

/**
 * Created by drferreira on 29/04/15.
 */

public enum TypeMessage {
    STATUS_MESSAGE("STATUS_MESSAGE"),
    SERVER_MESSAGE("SERVER_MESSAGE"),
    USER_MESSAGE("USER_MESSAGE");

    private String typeMessage;

    TypeMessage(String typeMessage) {
        this.typeMessage = typeMessage;
    }
}
