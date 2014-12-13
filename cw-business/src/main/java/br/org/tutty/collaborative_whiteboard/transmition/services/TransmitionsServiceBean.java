package br.org.tutty.collaborative_whiteboard.transmition.services;

import br.org.tutty.collaborative_whiteboard.context.Context;
import br.org.tutty.collaborative_whiteboard.exceptions.DataNotFoundException;
import br.org.tutty.collaborative_whiteboard.model.LoggedUser;
import br.org.tutty.collaborative_whiteboard.model.User;
import br.org.tutty.collaborative_whiteboard.transmition.exceptions.MessageMountException;
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
    private Context context;

    @Override
    public void connect(Session webSocketSession, HttpSession httpSession) {
        try {
            LoggedUser loggedUser = context.fetch(httpSession);

            if (!loggedUser.isActivityTransmition()) {
                loggedUser.activateTransmition(webSocketSession);
            }

        } catch (DataNotFoundException e) {
            sendMessage(MessageProblemsBuilder.getMessage(e), webSocketSession);
        }
    }

    @Override
    public void disconect(Session webSocketSession) {
        try {
            LoggedUser loggedUser = context.fetch(webSocketSession);
            loggedUser.disableTransmition();

        } catch (DataNotFoundException e) {
            sendMessage(MessageProblemsBuilder.getMessage(e), webSocketSession);
        }
    }

    @Override
    public void broadcast(String dataMessage, Session webSocketSessionSender) {
        try {
            final JSONObject messageFinal = putSenderData(dataMessage, webSocketSessionSender);

            webSocketSessionSender.getOpenSessions().forEach(s -> {
                if (!s.getId().equals(webSocketSessionSender.getId()))
                   sendMessage(messageFinal, s);
            });

        } catch (Exception e) {
            sendMessage(MessageProblemsBuilder.getMessage(e), webSocketSessionSender);
        }
    }

    private JSONObject putSenderData(String message, Session websocketSession) throws MessageMountException, DataNotFoundException {

        try {
            JSONObject dataMessage = new JSONObject(message);
            User loggedUser = context.fetch(websocketSession).getUser();

            return dataMessage.putOpt("user", loggedUser.getName());
        } catch (JSONException exception) {
            throw new MessageMountException(exception);
        }
    }

    private void sendMessage(JSONObject dataMessage,Session target){
        try {
            target.getBasicRemote().sendText(dataMessage.toString());

        } catch (IOException e) {}
    }
}