package cw.entities;

import br.org.tutty.util.PropertyMonitor;
import cw.dtos.EncryptorResources;
import cw.exceptions.DataNotFoundException;
import cw.exceptions.EncryptedException;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by drferreira on 12/12/14.
 */
@Entity
@Table(name = "user", catalog = "cw")
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Project> projects;

    @Transient
    public PropertyMonitor propertyMonitor = new PropertyMonitor(this);


    public User(String email, String password, String firstName, String lastName) throws EncryptedException {
        this.lastName = lastName;
        this.email = email;
        this.password = EncryptorResources.encrypt(password);
        this.firstName = firstName;
    }

    protected User() {
    }

    public String getFullName(){
        return getFirstName()+" "+getLastName();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void addProject(Project project){
        projects.add(project);
    }

    public List<Project> getProjects() throws DataNotFoundException {
        if(hasSomeProject()){
            return projects;
        }else {
            throw new DataNotFoundException();
        }
    }

    public Boolean hasSomeProject(){
        if(projects != null && !projects.isEmpty()){
            return true;
        }else {
            return false;
        }
    }

    public String getLastName() {
        return lastName;
    }
}
