package br.org.tutty.collaborative_whiteboard.transmition.model;

/**
 * Created by drferreira on 16/12/14.
 */
public class OnlineMessage extends StatusMessage {
    private static final String STATUS = "Online";

    public OnlineMessage() {
        super(STATUS);
    }
}
