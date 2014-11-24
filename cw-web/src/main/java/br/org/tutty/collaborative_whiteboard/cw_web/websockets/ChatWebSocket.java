package br.org.tutty.collaborative_whiteboard.cw_web.websockets;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/chat")
public class ChatWebSocket extends WebSocket{

	@OnMessage
	public void send(String dataMessage, Session session) throws IOException {
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

    private void broadcast(String message, Session session) throws IOException {
        for (Session loggedSession : session.getOpenSessions()){

            synchronized (loggedSession){
                loggedSession.getBasicRemote().sendText(message);
            }

            System.out.println("Mensagem de " +session.getId()+ " para " +loggedSession.getId()+ " Mensagem : " + message);
        }
    }

}
