package br.org.tutty.collaborative_whiteboard;

import cw.entities.Project;
import cw.entities.ProjectArea;
import cw.exceptions.DataNotFoundException;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by drferreira on 07/04/15.
 */
@Stateless
@Local(ProjectAreaDao.class)
public class ProjectAreaDaoBean extends GenericDao implements ProjectAreaDao{

    @Override
    public List<ProjectArea> filterProjectAreas(Project project, String queryName){
        Criteria criteria = createCriteria(ProjectArea.class);
        criteria.add(Restrictions.eq("project", project));
        criteria.add(Restrictions.ilike("name", "%"+queryName+"%"));

        return list(criteria);
    }

    @Override
    public List<ProjectArea> fetch(Project project) throws DataNotFoundException {
        Criteria criteria = createCriteria(ProjectArea.class);
        criteria.add(Restrictions.eq("project", project));

        return listNotWaitingEmpty(criteria);
    }

    @Override
    public ProjectArea fetch(Project project, String name) throws DataNotFoundException {
        Criteria criteria = createCriteria(ProjectArea.class);
        criteria.add(Restrictions.eq("project", project));
        criteria.add(Restrictions.eq("name", name));

        return (ProjectArea) uniqueResultNotWaitingEmpty(criteria);
    }

    @Override
    public ProjectArea fetchByPrefix(Project selectedProject, String prefixArea) throws DataNotFoundException {
        Criteria criteria = createCriteria(ProjectArea.class);
        criteria.add(Restrictions.eq("project", selectedProject));
        criteria.add(Restrictions.eq("prefix", prefixArea));

        return (ProjectArea) uniqueResultNotWaitingEmpty(criteria);
    }
}
