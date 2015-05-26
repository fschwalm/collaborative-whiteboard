package cw.entities;

import cw.dtos.json.JSonStage;
import cw.interfaces.ConvertibleToJSon;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by drferreira on 19/05/15.
 */
@Entity
@Table(name = "stage", catalog = "cw")
public class Stage implements Serializable, ConvertibleToJSon<JSonStage> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long position;

    public Stage() {
    }

    public Stage(String name) {
        this.name = name;
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

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    @Override
    public JSonStage toJson(){
        JSonStage jSonStage = new JSonStage();
        jSonStage.setName(name);
        jSonStage.setPosition(position);

        return jSonStage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stage stage = (Stage) o;

        if (name != null ? !name.equals(stage.name) : stage.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
