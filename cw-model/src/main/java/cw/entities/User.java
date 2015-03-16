package cw.entities;

import cw.dtos.EncryptorResources;
import cw.exceptions.DataNotFoundException;
import cw.exceptions.EncryptedException;

import javax.persistence.*;
import javax.persistence.criteria.Predicate;
import java.io.Serializable;
import java.util.List;

/**
 * Created by drferreira on 12/12/14.
 */
@Entity
@Table(name = "user", catalog = "cw")
public class User implements Serializable{

    @Id
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
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

    public List<Project> getProjects() throws DataNotFoundException {
        if(hasSomeProject()){
            return projects;
        }else {
            throw new DataNotFoundException();
        }
    }

    public Boolean hasSomeProject(){
        if(projects != null || projects.isEmpty()){
            return false;
        }else {
            return true;
        }
    }


}
