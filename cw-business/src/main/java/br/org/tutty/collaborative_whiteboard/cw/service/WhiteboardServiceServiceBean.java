package br.org.tutty.collaborative_whiteboard.cw.service;

import br.org.tutty.collaborative_whiteboard.WhiteboardDao;
import cw.dtos.json.Whiteboard;
import cw.entities.Stage;
import cw.exceptions.DataNotFoundException;
import org.json.JSONObject;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.websocket.Session;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * Created by drferreira on 19/05/15.
 */
@Stateless
@Local(WhiteboardService.class)
public class WhiteboardServiceServiceBean implements WhiteboardService, Serializable {

    private WhiteboardContext whiteboardContext;

    @Inject
    public WhiteboardDao whiteboardDao;

    @Override
    public List<Stage> fetchStages() throws DataNotFoundException {
        return whiteboardDao.fetchAllStages();
    }

    @Override
    public void createStage(Stage stage){
        whiteboardDao.persist(stage);
        // Buscar minhas session
        broadcast(new JSONObject(new Whiteboard()), );
    }

    @Override
    public void removeStage(Stage stage){
        whiteboardDao.remove(stage);
    }

    private void broadcast(JSONObject message, Session webSocketSessionSender) {
        webSocketSessionSender.getOpenSessions().stream()
                .forEach(s -> send(message, s));
    }

    private void send(JSONObject whiteboard, Session sessionTarget) {
        try {
            sessionTarget.getBasicRemote().sendText(whiteboard.toString());
        } catch (IOException e) {
        }
    }
}
