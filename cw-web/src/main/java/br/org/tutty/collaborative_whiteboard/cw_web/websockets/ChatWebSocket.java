package br.org.tutty.collaborative_whiteboard.cw_web.websockets;

import br.org.tutty.collaborative_whiteboard.transmition.model.UserMessage;
import br.org.tutty.collaborative_whiteboard.transmition.services.TransmitionsService;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/chat", configurator = GetHttpSessionConfigurator.class)
public class ChatWebSocket extends WebSocket {

    @Inject
    private TransmitionsService transmitionsService;

    @OnMessage
    public void send(String dataMessage, final Session senderSession) {
        UserMessage userMessage = new UserMessage(transmitionsService)
        transmitionsService.broadcast(new UserMessage(), senderSession);
    }

    @OnOpen
    public void open(final Session websocketSession, final EndpointConfig endpointConfig) {
        HttpSession httpSession = (HttpSession) endpointConfig.getUserProperties()
                .get(HttpSession.class.getName());

        transmitionsService.connect(websocketSession, httpSession);
    }

    @OnClose
    public void close(final Session session) {
        transmitionsService.disconect(session);
    }
}
