package br.org.tutty.collaborative_whiteboard.cw.filters;

import cw.entities.Stage;
import cw.exceptions.DataNotFoundException;

import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Created by drferreira on 28/07/15.
 */
public class StageFilters {

    public static Stage fetchFirstAfter(Stage stageReference, Set<Stage> stages) throws DataNotFoundException {
        if (stageReference != null) {
            try {
                return stages.stream()
                        .filter(stage -> stage.getPosition() > stageReference.getPosition())
                        .sorted((stageOne, stageTwo) -> Long.compare(stageOne.getPosition(), stageTwo.getPosition()))
                        .findFirst()
                        .get();
            } catch (NoSuchElementException e) {
                throw new DataNotFoundException();
            }
        } else {
            throw new DataNotFoundException();
        }
    }

    public static Stage fetchLastBefore(Stage stageReference, Set<Stage> stages) throws DataNotFoundException {
        if (stageReference != null) {
            try {
                return stages.stream()
                        .filter(stage -> stage.getPosition() < stageReference.getPosition())
                        .sorted((stageOne, stageTwo) -> Long.compare(stageTwo.getPosition(), stageOne.getPosition()))
                        .findFirst()
                        .get();
            } catch (NoSuchElementException e) {
                throw new DataNotFoundException();
            }
        } else {
            throw new DataNotFoundException();
        }

    }
}
