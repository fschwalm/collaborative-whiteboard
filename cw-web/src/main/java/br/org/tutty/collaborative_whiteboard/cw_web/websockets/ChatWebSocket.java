package br.org.tutty.collaborative_whiteboard.cw_web.websockets;

import br.org.tutty.collaborative_whiteboard.transmition.services.TransmitionsService;
import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/chat", configurator = GetHttpSessionConfigurator.class)
public class ChatWebSocket extends WebSocket {

    @Inject
    private TransmitionsService transmitionsService;

    @OnMessage
    public void send(String dataMessage, Session senderSession){

        try {
            String messageValue = new JSONObject(dataMessage).getString("messageValue");
            transmitionsService.send(messageValue, senderSession);
            System.out.println("+++++++++++++++++++++ Mensagem enviada +++++++++++++++++++++");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void open(Session websocketSession, EndpointConfig endpointConfig) {
        HttpSession httpSession = (HttpSession) endpointConfig.getUserProperties()
                .get(HttpSession.class.getName());

        transmitionsService.connect(websocketSession, httpSession);
        System.out.println("+++++++++++++++++++++ Conectado +++++++++++++++++++++");
    }

    @OnClose
    public void close(Session session) {
        System.out.println("+++++++++++++++++++++ Conex Fechada +++++++++++++++++++++");
        transmitionsService.disconect(session);
    }
}
