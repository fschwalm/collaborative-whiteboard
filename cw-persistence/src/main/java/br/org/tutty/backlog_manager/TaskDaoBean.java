package br.org.tutty.backlog_manager;

import backlog_manager.entities.Story;
import backlog_manager.entities.Task;
import br.org.tutty.collaborative_whiteboard.GenericDao;
import cw.exceptions.DataNotFoundException;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by drferreira on 25/05/15.
 */
@Stateless
@Local(TaskDao.class)
public class TaskDaoBean extends GenericDao implements TaskDao{

    @Override
    public Long getNextSequenceTask(Story project){
        Criteria criteria = createCriteria(Task.class);
        criteria.add(Restrictions.eq("story", project));

        criteria.setProjection(Projections.rowCount());

        return ((Long)criteria.list().get(0)).longValue();
    }

    @Override
    public List<Task> fetchByStory(Story selectedStory) throws DataNotFoundException {
        Criteria criteria = createCriteria(Task.class);
        criteria.add(Restrictions.eq("story", selectedStory));
        criteria.addOrder(Order.asc("id"));

        return (List<Task>) listNotWaitingEmpty(criteria);
    }

    @Override
    public List<Task> fetchAll(){
        Criteria criteria = createCriteria(Task.class);
        criteria.addOrder(Order.asc("id"));

        return (List<Task>) list(criteria);
    }



}
