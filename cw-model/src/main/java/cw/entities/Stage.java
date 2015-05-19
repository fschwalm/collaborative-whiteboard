package cw.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by drferreira on 19/05/15.
 */
@Entity
@Table(name = "stage", catalog = "cw")
public class Stage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer position;

    public Stage() {
    }

    public Stage(String name, Integer position) {
        this.name = name;
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
