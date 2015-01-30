package cw.entities;

import cw.dtos.EncryptorResources;
import cw.exceptions.EncryptedException;

import javax.persistence.*;
import java.util.List;

/**
 * Created by drferreira on 12/12/14.
 */
@Entity
@Table(name = "user", catalog = "cw")
public class User {

    @Id
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    private List<Project> projects;

    public User(String email, String password, String name) throws EncryptedException {
        this.email = email;
        this.password = EncryptorResources.encrypt(password);
        this.name = name;
    }

    protected User() {
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
