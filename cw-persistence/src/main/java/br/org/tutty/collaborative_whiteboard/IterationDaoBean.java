package br.org.tutty.collaborative_whiteboard;

import backlog_manager.entities.Iteration;
import backlog_manager.entities.Story;
import cw.exceptions.DataNotFoundException;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.Date;
import java.util.List;

/**
 * Created by drferreira on 23/06/15.
 */
@Stateless
@Local(IterationDao.class)
public class IterationDaoBean extends GenericDao implements IterationDao {

    @Override
    public List<Story> fetchStories(Iteration iteration) throws DataNotFoundException {
        Criteria criteria = createCriteria(Story.class);
        criteria.add(Restrictions.eq("iteration", iteration));

        return listNotWaitingEmpty(criteria);
    }

    @Override
    public Iteration getCurrentIteration() throws DataNotFoundException {
        Date now = new Date();

        Criteria criteria = createCriteria(Iteration.class);
        criteria.add(Restrictions.lt("initDate", now));
        criteria.add(Restrictions.gt("endDate", now));
        return (Iteration) uniqueResultNotWaitingEmpty(criteria);

    }
}
