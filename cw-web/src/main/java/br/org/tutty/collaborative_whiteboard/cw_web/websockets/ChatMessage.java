package br.org.tutty.collaborative_whiteboard.cw_web.websockets;

import java.io.Serializable;

/**
 * Created by drferreira on 21/11/14.
 */
class ChatMessage implements Serializable{

    private String message;

    public ChatMessage(String message) {
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
