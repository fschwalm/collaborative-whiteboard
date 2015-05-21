package br.org.tutty.collaborative_whiteboard.cw_web.websockets;

import br.org.tutty.collaborative_whiteboard.cw.service.WhiteboardService;
import br.org.tutty.collaborative_whiteboard.cw.handlers.WhiteboardHandler;
import cw.exceptions.DataNotFoundException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * Created by drferreira on 19/05/15.
 */
@ApplicationScoped
@ServerEndpoint(value = "/whiteboard", configurator = GetHttpSessionConfigurator.class)
public class WhiteboardWebSocket extends WebSocket {

    @Inject
    private WhiteboardHandler whiteboardHandler;

    @Inject
    private WhiteboardService whiteboardService;

    @OnMessage
    public void send(String dataMessage, Session senderSession){
        System.out.println("ENVIADA MENSAGEM");
    }

    @OnOpen
    public void open(Session websocketSession, EndpointConfig endpointConfig) throws DataNotFoundException {
        whiteboardHandler.addSession(websocketSession);
        whiteboardService.refreshWhiteboard(websocketSession);
    }

    @OnClose
    public void close(Session session) throws IOException {
        whiteboardHandler.removeSession(session);
    }
}
