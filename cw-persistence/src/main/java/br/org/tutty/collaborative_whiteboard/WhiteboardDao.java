package br.org.tutty.collaborative_whiteboard;

import backlog_manager.entities.Story;
import cw.dtos.json.JSonStage;
import cw.dtos.json.JSonStory;
import cw.entities.Stage;
import cw.exceptions.DataNotFoundException;

import java.util.List;
import java.util.Set;

/**
 * Created by drferreira on 19/05/15.
 */
public interface WhiteboardDao extends Dao {
    Set<Stage> fetchAllStages() throws DataNotFoundException;

    Set<Story> fetchStories();

    Set<JSonStage> mountJsonStages();

    Set<JSonStory> mountJsonStories();
}
