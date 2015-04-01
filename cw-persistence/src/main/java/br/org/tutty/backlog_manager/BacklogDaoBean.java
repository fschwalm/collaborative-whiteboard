package br.org.tutty.backlog_manager;

import backlog_manager.entities.Story;
import br.org.tutty.collaborative_whiteboard.GenericDao;
import cw.entities.Project;
import cw.exceptions.DataNotFoundException;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import java.util.List;

/**
 * Created by drferreira on 11/03/15.
 */
public class BacklogDaoBean extends GenericDao implements BacklogDao {

    @Override
    public List<Story> fetchAllStories() throws DataNotFoundException {
        Criteria criteria = createCriteria(Story.class);
        criteria.addOrder(Order.desc("priority"));

        return listNotWaitingEmpty(criteria);
    }

    @Override
    public Long getNextSequenceStory(Project project){
        Criteria criteria = createCriteria(Story.class);
        criteria.add(Restrictions.eq("project", project));

        criteria.setProjection(Projections.rowCount());

        return ((Long)criteria.list().get(0)).longValue();
    }
}
