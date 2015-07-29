package br.org.tutty.collaborative_whiteboard.cw.service;

import cw.entities.Stage;
import cw.exceptions.DataNotFoundException;

import javax.websocket.Session;
import java.util.Set;

/**
 * Created by drferreira on 19/05/15.
 */
public interface WhiteboardService {
    Set<Stage> fetchStages() throws DataNotFoundException;

    void createStage(Stage stage) throws DataNotFoundException;

    void refreshAllWhiteboards();

    void refreshWhiteboard(Session target);

    void removeStage(Stage stage);

    Stage fetchPreviousStage(Stage stageReference) throws DataNotFoundException;

    Stage fetchNextStage(Stage stageReference) throws DataNotFoundException;
}
