package br.org.tutty.collaborative_whiteboard.backlog_manager.services;

import backlog_manager.entities.Iteration;
import backlog_manager.entities.Story;
import backlog_manager.entities.StoryStatusLog;
import backlog_manager.enums.StoryStatus;
import br.org.tutty.collaborative_whiteboard.IterationDao;
import cw.exceptions.DataNotFoundException;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by drferreira on 23/06/15.
 */

@Stateless
@Local(IterationService.class)
public class IterationServiceBean implements IterationService {

    @Inject
    private IterationDao iterationDao;

    @Inject
    private BacklogManagerService backlogManagerService;

    @Override
    public Iteration getCurrentIteration() throws DataNotFoundException {
        return iterationDao.getCurrentIteration();
    }

    @Override
    public List<Story> fetchStoriesForIteration(){
        try{
            List<Story> analyzedStories = backlogManagerService.fetchAnalyzedStories();

            return analyzedStories.stream().filter(story -> story.getIteration() == null).collect(Collectors.toList());
        }catch (DataNotFoundException e){
            return new ArrayList<>();
        }
    }

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

    @Override
    public Float getProgressIteration(Iteration iteration) {
        try{
            List<Story> stories = iterationDao.fetchStories(iteration);

            Integer total = stories.size();
            Integer numberOfFinalized = 0;

            for(Story story : stories){
                try{
                    StoryStatusLog storyStatus = backlogManagerService.getStoryStatus(story);

                    if(StoryStatus.FINALIZED.equals(storyStatus.getStoryStatus())){
                        numberOfFinalized++;
                    }

                }catch (DataNotFoundException e){}
            }

            return new Float ((numberOfFinalized * 100) / total);
        }catch (DataNotFoundException e){
            return 0f;
        }
    }
}
