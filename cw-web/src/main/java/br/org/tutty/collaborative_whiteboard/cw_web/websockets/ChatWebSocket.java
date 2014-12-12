package br.org.tutty.collaborative_whiteboard.cw_web.websockets;

import br.org.tutty.collaborative_whiteboard.transmition.ChatMessageBuilder;
import br.org.tutty.collaborative_whiteboard.transmition.services.TransmitionsService;
import br.org.tutty.collaborative_whiteboard.transmition.exceptions.MessageMountException;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/chat", configurator = GetHttpSessionConfigurator.class)
public class ChatWebSocket extends WebSocket{

    @Inject
    private TransmitionsService transmitionsService;

	@OnMessage
	public void send(String dataMessage, final Session session){
        broadcast(dataMessage, session);
    }

    @OnOpen
	public void open(final Session websocketSession, final EndpointConfig endpointConfig){
        HttpSession httpSession = (HttpSession) endpointConfig.getUserProperties()
                .get(HttpSession.class.getName());

        System.out.println("HTTP SESSION ID :" + httpSession.getId());
        System.out.println("WEBSOCKET ID :" + websocketSession.getId());

        transmitionsService.connect(websocketSession, httpSession);
	}

	@OnClose
	public void close(final Session session){
        transmitionsService.disconect(session);
	}

    private void broadcast(String message, final Session senderSession) {
        ChatMessageBuilder chatMessageBuilder = new ChatMessageBuilder(message, , senderSession);

        try {
            transmitionsService.broadcast(chatMessageBuilder, senderSession);
        } catch (MessageMountException e) {
            e.printStackTrace();
        }
    }
}
