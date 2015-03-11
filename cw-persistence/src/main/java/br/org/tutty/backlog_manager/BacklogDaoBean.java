package br.org.tutty.backlog_manager;

import backlog_manager.entities.Story;
import br.org.tutty.collaborative_whiteboard.GenericDao;
import cw.exceptions.DataNotFoundException;
import org.hibernate.Criteria;

import java.util.List;

/**
 * Created by drferreira on 11/03/15.
 */
public class BacklogDaoBean extends GenericDao implements BacklogDao {

    @Override
    public List<Story> fetchStories() throws DataNotFoundException {
        Criteria criteria = createCriteria(Story.class);

        return listNotWaitingEmpty(criteria);
    }
}
