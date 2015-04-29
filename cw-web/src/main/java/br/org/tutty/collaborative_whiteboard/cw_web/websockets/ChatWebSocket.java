package br.org.tutty.collaborative_whiteboard.cw_web.websockets;

import br.org.tutty.collaborative_whiteboard.transmition.services.TransmitionsService;
import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/chat", configurator = GetHttpSessionConfigurator.class)
public class ChatWebSocket extends WebSocket {

    @Inject
    private TransmitionsService transmitionsService;

    @OnMessage
    public void send(String dataMessage, Session senderSession){

        try {
            String messageValue = new JSONObject(dataMessage).getString("messageValue");
            transmitionsService.send(messageValue, senderSession);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void open(Session websocketSession, EndpointConfig endpointConfig) {
        HttpSession httpSession = (HttpSession) endpointConfig.getUserProperties()
                .get(HttpSession.class.getName());

        transmitionsService.connect(websocketSession, httpSession);
    }

    @OnClose
    public void close(Session session) throws IOException {
        transmitionsService.disconect(session);
    }
}
