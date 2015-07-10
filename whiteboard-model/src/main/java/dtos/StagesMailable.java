package dtos;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by drferreira on 10/07/15.
 */
public class StagesMailable implements Serializable{
    private Set<StoryMailable> stories;
    private String name;
    private Long position;

    public StagesMailable(Set<StoryMailable> stories) {
        this.stories = stories;
    }

    public Set<StoryMailable> getStories() {
        return stories;
    }

    public void setStories(Set<StoryMailable> stories) {
        this.stories = stories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
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
