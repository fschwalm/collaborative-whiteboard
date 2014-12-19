package br.org.tutty.collaborative_whiteboard.transmition.model.messages;

/**
 * Created by drferreira on 16/12/14.
 */
public class OfflineMessage extends StatusMessage {
    private static final String STATUS = "Offline";

    public OfflineMessage() {
        super(STATUS);
    }
}
