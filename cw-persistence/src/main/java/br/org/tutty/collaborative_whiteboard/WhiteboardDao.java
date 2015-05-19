package br.org.tutty.collaborative_whiteboard;

import cw.entities.Stage;
import cw.exceptions.DataNotFoundException;

import java.util.List;

/**
 * Created by drferreira on 19/05/15.
 */
public interface WhiteboardDao extends Dao {
    List<Stage> fetchAllStages() throws DataNotFoundException;
}
