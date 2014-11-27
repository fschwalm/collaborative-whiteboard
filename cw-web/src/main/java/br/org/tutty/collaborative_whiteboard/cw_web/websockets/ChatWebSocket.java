package br.org.tutty.collaborative_whiteboard.cw_web.websockets;

import br.org.tutty.collaborative_whiteboard.context.ChatContextService;
import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/chat")
public class ChatWebSocket extends WebSocket{

    @Inject
    private ChatContextService chatContextService;

	@OnMessage
	public void send(String dataMessage, Session session) throws IOException, JSONException {
        broadcast(dataMessage, session);
    }

    @OnOpen
	public void open(Session session){
		System.out.println("Conexão realizada (id) : "+session.getId());
	}

	@OnClose
	public void close(Session session){
        System.out.println("Conexão finalizada (id) : "+session.getId());
	}

    private void broadcast(String message, Session session) {
        try {
            JSONObject jsonObject = new JSONObject(message);
            jsonObject.putOpt("user", "Usuario");

            for (Session loggedSession : session.getOpenSessions()) {

                synchronized (loggedSession) {
                    if(! session.getId().equals(loggedSession.getId())){
                        loggedSession.getBasicRemote().sendText(jsonObject.toString());
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
