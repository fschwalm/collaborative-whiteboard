package cw.entities;

import cw.dtos.EncryptorResources;
import cw.exceptions.EncryptedException;

import javax.persistence.*;
import java.util.List;

/**
 * Created by drferreira on 12/12/14.
 */
@Entity
@Table(name = "user", catalog = "cw", schema = "cw")
public class User {

    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Project> projects;

    public User(String email, String password, String name) throws EncryptedException {
        this.email = email;
        this.password = EncryptorResources.encrypt(password);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<Project> getProjects() {
        return projects;
    }
}
