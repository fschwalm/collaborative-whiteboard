package br.org.tutty.backlog_manager;

import backlog_manager.entities.Story;
import backlog_manager.entities.StoryStatusLog;
import backlog_manager.entities.Task;
import backlog_manager.entities.TaskStatusLog;
import backlog_manager.enums.StoryStatus;
import backlog_manager.enums.TaskStatus;
import br.org.tutty.collaborative_whiteboard.GenericDao;
import cw.exceptions.DataNotFoundException;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by drferreira on 25/05/15.
 */
@Stateless
@Local(TaskDao.class)
public class TaskDaoBean extends GenericDao implements TaskDao{
    @Inject
    private StoryDao storyDao;

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

    @Override
    public List<Task> fetchForWhiteboard(){
        List<Story> stories = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();
        try {
            stories.addAll(storyDao.fetchStories(StoryStatus.ANALYZED));

            if(!stories.isEmpty()){
                Criteria criteria = createCriteria(Task.class);
                criteria.addOrder(Order.asc("id"));
                criteria.add(Restrictions.in("story", stories));
                criteria.add(Restrictions.isNotNull("stage"));
                tasks.addAll(((List<Task>)list(criteria))) ;

                return tasks;
            }else {
                return tasks;
            }

        } catch (DataNotFoundException e) {
            return tasks;
        }
    }

    @Override
    public TaskStatusLog fetchTaskStatusLog(Task task) throws DataNotFoundException {
        Criteria criteria = createCriteria(TaskStatusLog.class);
        criteria.add(Restrictions.eq("task", task));
        criteria.addOrder(Order.desc("date"));
        criteria.setFirstResult(0);
        criteria.setMaxResults(1);

        return (TaskStatusLog) uniqueResultNotWaitingEmpty(criteria);
    }

    @Override
    public Task fetchByCode(String taskcode) throws DataNotFoundException {
        Criteria criteria = createCriteria(Task.class);
        criteria.add(Restrictions.eq("code", taskcode));

        return (Task) uniqueResultNotWaitingEmpty(criteria);
    }
}
