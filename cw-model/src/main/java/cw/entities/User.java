package cw.entities;

import br.org.tutty.util.PropertyMonitor;
import cw.dtos.EncryptorResources;
import cw.exceptions.DataNotFoundException;
import cw.exceptions.EncryptedException;
import org.apache.commons.io.IOUtils;

import javax.persistence.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by drferreira on 12/12/14.
 */
@Entity
@Table(name = "user", catalog = "cw")
public class User implements Serializable {

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

    @Lob
    private byte[] profilePicture;

    private Date birthdate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Project> projects;

    @Transient
    public PropertyMonitor propertyMonitor = new PropertyMonitor(this);


    public User(String email, String password, String firstName, String lastName, Date birthdate, InputStream profilePicture) throws EncryptedException, IOException {
        this.lastName = lastName;
        this.email = email;
        this.password = EncryptorResources.encrypt(password);
        this.firstName = firstName;
        this.birthdate = birthdate;
        this.profilePicture = IOUtils.toByteArray(profilePicture);
    }

    protected User() {
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
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

    public void addProject(Project project) {
        projects.add(project);
    }

    public List<Project> getProjects() throws DataNotFoundException {
        if (hasSomeProject()) {
            return projects;
        } else {
            throw new DataNotFoundException();
        }
    }

    public Boolean hasSomeProject() {
        if (projects != null && !projects.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public String getLastName() {
        return lastName;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
