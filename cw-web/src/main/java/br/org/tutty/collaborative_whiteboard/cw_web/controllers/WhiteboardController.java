package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

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

@ServerEndpoint("/whiteboard")
public class WhiteboardController implements Serializable{

	private static final long serialVersionUID = -6203102981536539710L;
	private Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());
	
	@OnMessage
	public String receiveMessage(String message, Session session){
		return "Mensagem" +message+ ", session:" + session.getId();
	}
	
	@OnOpen
	public void open(Session session){
		sessions.add(session);
		System.out.println("Minha sessao:"+session.getId());
	}
	
	@OnClose
	public void close(Session session, CloseReason closeReason){
		sessions.remove(session);
	}
	
}
