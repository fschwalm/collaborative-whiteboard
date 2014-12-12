package br.org.tutty.collaborative_whiteboard.cw_web.websockets;

import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONException;
import org.json.JSONObject;

import br.org.tutty.collaborative_whiteboard.context.ChatService;

@ServerEndpoint("/chat")
public class ChatWebSocket extends WebSocket {

	private static final long serialVersionUID = -7885444123737673755L;

	@Inject
	private ChatService chatService;

	@OnMessage
	public void send(String dataMessage, Session session) {
		broadcast(dataMessage, session);
	}

	@OnOpen
	public void open(Session session) {
		chatService.connect(session);
	}

	@OnClose
	public void close(Session session) {
		chatService.disconect(session);
	}

	private void broadcast(String message, Session session) {
		try {
			JSONObject jsonObject = new JSONObject(message);
			jsonObject.putOpt("user", "Usuario");

			chatService.broadcast(jsonObject, session);
		} catch (JSONException e) {
			// TODO Mensagem de ERRO ao enviar
		}
	}
}
