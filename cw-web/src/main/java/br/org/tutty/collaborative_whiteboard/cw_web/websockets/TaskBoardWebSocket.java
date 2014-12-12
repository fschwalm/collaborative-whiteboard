package br.org.tutty.collaborative_whiteboard.cw_web.websockets;

import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import br.org.tutty.collaborative_whiteboard.context.BroadCastService;

@ServerEndpoint("/taskboard")
public class TaskBoardWebSocket extends WebSocket {

	private static final long serialVersionUID = -600539762526738573L;
	
	@Inject
	private BroadCastService broadCastService;
	
	@OnMessage
	public void send(String dataMessage, Session session) {
		broadCastService.updateClients(dataMessage, session);
		System.out.println("Dados: " + dataMessage + " Sessão: " + session.getId());
	}

	@OnOpen
	public void open(Session session) {
		System.out.println("Conectou!");
	}

	@OnClose
	public void close(Session session) {
		System.out.println("Desconectou!");
	}
	
}
