package br.org.tutty.collaborative_whiteboard.cw_web.websockets;

import java.io.Serializable;

/**
 * Created by drferreira on 21/11/14.
 */
class ChatMessage implements Serializable {

	private static final long serialVersionUID = 160811907709048055L;

	private String message;

	public ChatMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
