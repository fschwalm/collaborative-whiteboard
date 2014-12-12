package br.org.tutty.collaborative_whiteboard.context;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.Local;
import javax.websocket.Session;

import org.json.JSONObject;

/**
 * Created by drferreira on 27/11/14.
 */
@Local(ChatService.class)
public class ChatServiceBean implements ChatService, Serializable {

	private static final long serialVersionUID = -6129988492756952572L;

	@Override
	public void connect(Session session) {
		// TODO BUSCAR E TORNAR DISPONIVEL USUARIO
	}

	@Override
	public void disconect(Session session) {
		// TODO ANALIZAR LOGOUT USUARIO
	}

	public void broadcast(JSONObject jsonMessage, Session session) {
		session.getOpenSessions().forEach(s -> {
			if (!s.getId().equals(session.getId()))
				try {
					s.getBasicRemote().sendText(jsonMessage.toString());
				} catch (IOException e) {
				}
		});
	}
}