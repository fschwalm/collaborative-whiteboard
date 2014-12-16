package br.org.tutty.collaborative_whiteboard.transmition.model;

/**
 * Created by drferreira on 16/12/14.
 */
public class StatusMessage extends Message{
    private String status;
    private String message;

    public StatusMessage(String status, String message) {
        super(TypeMessage.STATUS_MESSAGE);
    }

    @Override
    public void aditionalWrite() {
        write("STATUS", status);
        write("MESSAGE", message);
    }
}
