package br.org.tutty.collaborative_whiteboard;

import cw.entities.Project;
import cw.entities.ProjectArea;
import cw.entities.User;
import cw.exceptions.DataNotFoundException;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by drferreira on 20/03/15.
 */
@Stateless
@Local(ProjectDao.class)
public class ProjectDaoBean extends GenericDao implements ProjectDao {

    @Override
    public Project fetch(String name) throws DataNotFoundException {
        Criteria criteria = createCriteria(Project.class);
        criteria.add(Restrictions.eq("nameProject", name));

        return (Project) uniqueResultNotWaitingEmpty(criteria);
    }

    public List fetchAll() throws DataNotFoundException {
        Criteria criteria = createCriteria(Project.class);
        return listNotWaitingEmpty(criteria);

    }
}
