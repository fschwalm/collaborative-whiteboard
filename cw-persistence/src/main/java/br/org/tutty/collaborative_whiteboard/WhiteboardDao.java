package br.org.tutty.collaborative_whiteboard;

import cw.entities.Stage;
import cw.exceptions.DataNotFoundException;

import java.util.List;
import java.util.Set;

/**
 * Created by drferreira on 19/05/15.
 */
public interface WhiteboardDao extends Dao {
    Set<Stage> fetchAllStages() throws DataNotFoundException;
}
