package br.org.tutty.collaborative_whiteboard.transmition.services;

import br.org.tutty.collaborative_whiteboard.cw.context.UserContext;
import br.org.tutty.collaborative_whiteboard.cw.exceptions.DataNotFoundException;
import br.org.tutty.collaborative_whiteboard.cw.model.LoggedUser;
import br.org.tutty.collaborative_whiteboard.cw.model.User;
import br.org.tutty.collaborative_whiteboard.transmition.exceptions.MessageMountException;
import br.org.tutty.collaborative_whiteboard.transmition.model.Message;
import br.org.tutty.collaborative_whiteboard.transmition.model.OfflineMessage;
import br.org.tutty.collaborative_whiteboard.transmition.model.OnlineMessage;
import br.org.tutty.collaborative_whiteboard.transmition.model.UserMessage;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ejb.Local;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by drferreira on 27/11/14.
 */
@Local(TransmitionsService.class)
public class TransmitionsServiceBean implements TransmitionsService, Serializable {

    @Inject
    private UserContext userContext;

    @Override
    public void connect(Session webSocketSession, HttpSession httpSession) {
        try {
            LoggedUser loggedUser = userContext.fetch(httpSession);

            if (!loggedUser.isActivityTransmition()) {
                loggedUser.activateTransmition(webSocketSession);

                OnlineMessage onlineMessage = new OnlineMessage();
                sendMessage(onlineMessage.toJSon(), webSocketSession);
            }

        } catch (DataNotFoundException e) {
            OfflineMessage offlineMessage = new OfflineMessage();
            sendMessage(offlineMessage.toJSon(), webSocketSession);
        }
    }

    @Override
    public void send(String messageData, Session webSocketSessionSender) {
        try {
            LoggedUser loggedUser = userContext.fetch(webSocketSessionSender);
            String userName = loggedUser.getUser().getName();

            UserMessage userMessage = new UserMessage(userName, messageData);
            broadcast(userMessage, webSocketSessionSender);

        } catch (DataNotFoundException e) {
            OfflineMessage offlineMessage = new OfflineMessage();
            sendMessage(offlineMessage.toJSon(), webSocketSessionSender);
        }
    }

    @Override
    public void disconect(Session webSocketSession) {
        try {
            LoggedUser loggedUser = userContext.fetch(webSocketSession);
            loggedUser.disableTransmition();

        } catch (DataNotFoundException e) {
        }

        OfflineMessage offlineMessage = new OfflineMessage();
        sendMessage(offlineMessage.toJSon(), webSocketSession);
    }

    private void broadcast(Message message, Session webSocketSessionSender) {
        String websocketIdSender = webSocketSessionSender.getId();

        webSocketSessionSender.getOpenSessions().stream()
                .forEach(s -> sendMessage(message.toJSon(), s));
    }

    private void sendMessage(JSONObject dataMessage, Session target) {
        try {
            target.getBasicRemote().sendText(dataMessage.toString());
        } catch (IOException e) {
        }
    }
}