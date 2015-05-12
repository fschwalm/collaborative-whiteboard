package br.org.tutty.collaborative_whiteboard;

import cw.entities.Project;
import cw.entities.User;
import cw.exceptions.DataNotFoundException;

import java.util.List;

/**
 * Created by drferreira on 20/03/15.
 */
public interface ProjectDao extends Dao {
    Project fetch(String name) throws DataNotFoundException;

    List<Project> fetchAll() throws DataNotFoundException;
}
