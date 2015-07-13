package br.org.tutty.collaborative_whiteboard;

import cw.entities.Stage;

import java.util.Set;

/**
 * Created by drferreira on 19/05/15.
 */
public interface WhiteboardDao extends Dao {
    Set<Stage> fetchAllStages();

    Stage fetchInitialStage();
}
