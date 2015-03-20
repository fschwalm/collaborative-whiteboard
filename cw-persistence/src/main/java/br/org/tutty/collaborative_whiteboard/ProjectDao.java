package br.org.tutty.collaborative_whiteboard;

import cw.entities.Project;
import cw.exceptions.DataNotFoundException;

/**
 * Created by drferreira on 20/03/15.
 */
public interface ProjectDao extends Dao {
    Project fetch(String name) throws DataNotFoundException;
}
