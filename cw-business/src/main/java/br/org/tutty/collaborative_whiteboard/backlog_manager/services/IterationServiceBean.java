package br.org.tutty.collaborative_whiteboard.backlog_manager.services;

import backlog_manager.entities.Iteration;
import backlog_manager.entities.Story;
import br.org.tutty.collaborative_whiteboard.IterationDao;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by drferreira on 23/06/15.
 */

@Stateless
@Local(IterationService.class)
public class IterationServiceBean implements IterationService {

    @Inject
    private IterationDao iterationDao;

    @Override
    public void create(List<Story> stories, String name, Date init, Date end){
        Iteration iteration = new Iteration(name, init, end);

        iterationDao.persist(iteration);
        iterationDao.merge(iteration);

        stories.forEach(new Consumer<Story>() {
            @Override
            public void accept(Story story) {
                story.setIteration(iteration);
                iterationDao.update(story);
            }
        });
    }

    public void end(){

    }
}
