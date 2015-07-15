package dtos;

import br.org.tutty.Equalization;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by drferreira on 10/07/15.
 */
public class StagesMailable implements Serializable {
    private Set<StoryMailable> stories;

    @Equalization(name = "stage_name")
    private String name;

    @Equalization(name = "stage_position")
    private Long position;

    public StagesMailable() {
        stories = new HashSet<>();
    }

    public void addStory(StoryMailable storyMailable) {
        stories.add(storyMailable);
    }

    public Boolean existStory(String storyCode) {
        return stories.stream().anyMatch(story -> story.getCode().equals(storyCode));
    }

    public StoryMailable getStoryByCode(String code) {
        return stories.stream().filter(story -> story.getCode().equals(code)).findAny().get();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StagesMailable that = (StagesMailable) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
