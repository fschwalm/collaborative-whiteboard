package br.org.tutty.collaborative_whiteboard.transmition.model;

/**
 * Created by drferreira on 16/12/14.
 */
public class StatusMessage extends Message{
    private String status;

    public StatusMessage(String status) {
        super(TypeMessage.STATUS_MESSAGE);
        this.status = status;
    }

    @Override
    public void aditionalWrite() {
        write("STATUS", status);
    }
}
