package br.org.tutty.backlog_manager;

import backlog_manager.entities.Analysis;
import backlog_manager.entities.Story;
import backlog_manager.entities.StoryStatusLog;
import backlog_manager.entities.UploadedFile;
import br.org.tutty.collaborative_whiteboard.GenericDao;
import cw.entities.Project;
import cw.entities.ProjectArea;
import cw.exceptions.DataNotFoundException;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by drferreira on 11/03/15.
 */

public class StoryDaoBean extends GenericDao implements StoryDao {

    @Override
    public List<Story> fetchAllStories() throws DataNotFoundException {
        Criteria criteria = createCriteria(Story.class);
        criteria.addOrder(Order.asc("priority"));

        return listNotWaitingEmpty(criteria);
    }

    @Override
    public Long getNextSequenceStory(Project project){
        Criteria criteria = createCriteria(Story.class);
        criteria.add(Restrictions.eq("project", project));

        criteria.setProjection(Projections.rowCount());

        return ((Long)criteria.list().get(0)).longValue();
    }

    @Override
    public List<Story> fetchStories(ProjectArea projectArea) throws DataNotFoundException {
        Criteria criteria = createCriteria(Story.class);
        criteria.add(Restrictions.eq("projectArea", projectArea));

        return listNotWaitingEmpty(criteria);
    }

    @Override
    public StoryStatusLog getStoryStatus(Story story) throws DataNotFoundException {
        Criteria criteria = createCriteria(StoryStatusLog.class);
        criteria.add(Restrictions.eq("story", story));
        criteria.addOrder(Order.desc("date"));
        criteria.setFirstResult(0);
        criteria.setMaxResults(1);

        return (StoryStatusLog) uniqueResultNotWaitingEmpty(criteria);
    }

    @Override
    public Analysis getLastStoryAnalysis(Story story) throws DataNotFoundException {
        Criteria criteria = createCriteria(Analysis.class);
        criteria.add(Restrictions.eq("story", story));
        criteria.addOrder(Order.desc("id"));
        criteria.setFirstResult(0);
        criteria.setMaxResults(1);

        return (Analysis) uniqueResultNotWaitingEmpty(criteria);
    }

    @Override
    public List<UploadedFile> fetchFiles(Story selectedStory) throws DataNotFoundException {
        Criteria criteria = createCriteria(UploadedFile.class);
        criteria.add(Restrictions.eq("story", selectedStory));

        return listNotWaitingEmpty(criteria);
    }
}
