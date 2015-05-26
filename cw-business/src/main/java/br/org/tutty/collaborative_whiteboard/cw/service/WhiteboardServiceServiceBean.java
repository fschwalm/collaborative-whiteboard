package br.org.tutty.collaborative_whiteboard.cw.service;

import backlog_manager.entities.Story;
import br.org.tutty.collaborative_whiteboard.WhiteboardDao;
import br.org.tutty.collaborative_whiteboard.cw.handlers.WhiteboardHandler;
import com.google.gson.Gson;
import cw.dtos.json.JSonStage;
import cw.dtos.json.JSonStory;
import cw.dtos.json.Whiteboard;
import cw.entities.Stage;
import cw.exceptions.DataNotFoundException;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.websocket.Session;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

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
    public Set<Stage> fetchStages() throws DataNotFoundException {
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
    public void removeStage(Stage stage) {
        whiteboardDao.remove(stage);
    }

    public Whiteboard mountWhiteboard() {
        Set<JSonStage> stages = whiteboardDao.mountJsonStages();
        Set<JSonStory> stories = whiteboardDao.mountJsonStories();

        return whiteboardHandler.builderWhiteboard(stages, stories);
    }

    @Override
    public void refreshAllWhiteboards() {
        Whiteboard whiteboard = mountWhiteboard();
        whiteboardHandler.broadcast(new Gson().toJson(whiteboard));
    }

    @Override
    public void refreshWhiteboard(Session target) {
        Whiteboard whiteboard = mountWhiteboard();
        whiteboardHandler.send(new Gson().toJson(whiteboard), target);
    }


}
