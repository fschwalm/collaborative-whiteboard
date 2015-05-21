package br.org.tutty.collaborative_whiteboard.cw.service;

import br.org.tutty.collaborative_whiteboard.WhiteboardDao;
import br.org.tutty.collaborative_whiteboard.cw.handlers.WhiteboardHandler;
import com.google.gson.Gson;
import cw.dtos.json.Whiteboard;
import cw.entities.Stage;
import cw.exceptions.DataNotFoundException;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.websocket.Session;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;

/**
 * Created by drferreira on 19/05/15.
 */
@Stateless
@Local(WhiteboardService.class)
public class WhiteboardServiceServiceBean implements WhiteboardService, Serializable {

    @Inject
    private WhiteboardHandler whiteboardHandler;

    @Inject
    public WhiteboardDao whiteboardDao;

    @Override
    public List<Stage> fetchStages() throws DataNotFoundException {
        return whiteboardDao.fetchAllStages();
    }

    @Override
    public void createStage(Stage stage) throws DataNotFoundException {
        Long count = whiteboardDao.count(Stage.class);
        stage.setPosition(count);
        whiteboardDao.persist(stage);

        refreshAllWhiteboards();
    }

@Override
    public void refreshAllWhiteboards() throws DataNotFoundException {
        List<Stage> stages = whiteboardDao.fetchAllStages();
        Whiteboard whiteboard = whiteboardHandler.builderWhiteboard(stages, new HashSet<>());
        whiteboardHandler.broadcast(new Gson().toJson(whiteboard));
    }

    @Override
    public void refreshWhiteboard(Session target) throws DataNotFoundException {
        List<Stage> stages = whiteboardDao.fetchAllStages();
        Whiteboard whiteboard = whiteboardHandler.builderWhiteboard(stages, new HashSet<>());
        whiteboardHandler.send(new Gson().toJson(whiteboard), target);
    }

    @Override
    public void removeStage(Stage stage){
        whiteboardDao.remove(stage);
    }


}
