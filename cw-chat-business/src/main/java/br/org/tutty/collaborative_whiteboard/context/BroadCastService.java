package br.org.tutty.collaborative_whiteboard.context;

import javax.websocket.Session;

import org.json.JSONObject;

public interface BroadCastService {

	void updateClients(JSONObject jsonMessage, Session session);

	void updateClients(String message, Session session);
	
}
