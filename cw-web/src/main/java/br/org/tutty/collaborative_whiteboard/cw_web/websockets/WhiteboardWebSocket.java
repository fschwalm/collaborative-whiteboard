package br.org.tutty.collaborative_whiteboard.cw_web.websockets;

import br.org.tutty.collaborative_whiteboard.cw.service.WhiteboardService;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * Created by drferreira on 19/05/15.
 */
@ServerEndpoint(value = "/whiteboard", configurator = GetHttpSessionConfigurator.class)
public class WhiteboardWebSocket extends WebSocket {

    @Inject
    private WhiteboardService whiteboardService;

    @OnMessage
    public void send(String dataMessage, Session senderSession){
        System.out.print("ENVIADA MENSAGEM");
    }

    @OnOpen
    public void open(Session websocketSession, EndpointConfig endpointConfig) {
        System.out.print("CONECTADO");
    }

    @OnClose
    public void close(Session session) throws IOException {
        System.out.print("FECHADO");
    }
}
