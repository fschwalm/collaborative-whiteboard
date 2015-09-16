package br.org.tutty.collaborative_whiteboard.cw_web.dtos;

import br.org.tutty.util.PropertyMonitor;
import cw.entities.User;
import cw.exceptions.DataNotFoundException;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by drferreira on 25/05/15.
 */
public class UserEdition implements Serializable {
	@Inject
	private ProfilePicture profilePicture;

	public User user;
	private String firstUserName;
	private String lastUserName;
	private String email;
	private Date birthdate;
	public PropertyMonitor propertyMonitor = new PropertyMonitor(this);

	public void init(User user) throws DataNotFoundException {
		if (user != null) {
			this.user = user;
			this.firstUserName = user.getFirstName();
			this.lastUserName = user.getLastName();
			this.email = user.getEmail();
			this.birthdate = user.getBirthdate();
			this.profilePicture.loadPicture();
		} else {
			throw new DataNotFoundException();
		}
	}

	public User toEntity(){
		this.user.setFirstName(firstUserName);
		this.user.setLastName(lastUserName);
		this.user.setEmail(email);
		this.user.setBirthdate(birthdate);
		this.user.setProfilePicture(profilePicture.getProfilePicture());
		
		return user;
	}

	public String getFirstUserName() {
		return firstUserName;
	}

	public void setFirstUserName(String firstUserName) {
		String oldValue = this.firstUserName;
		this.firstUserName = firstUserName;

		propertyMonitor.getPropertyChangeSupport().firePropertyChange("firstNmae", oldValue, firstUserName);
	}

	public String getLastUserName() {
		return lastUserName;
	}

	public void setLastUserName(String lastUserName) {
		String oldValue = this.lastUserName;
		this.lastUserName = lastUserName;


		propertyMonitor.getPropertyChangeSupport().firePropertyChange("lastUserName", oldValue, lastUserName);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		String oldValue = this.email;
		this.email = email;

		propertyMonitor.getPropertyChangeSupport().firePropertyChange("email", oldValue, email);
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		Date oldValue = this.birthdate;
		this.birthdate = birthdate;

		propertyMonitor.getPropertyChangeSupport().firePropertyChange("birthdate", oldValue, birthdate);
	}

	public ProfilePicture getProfilePicture(){
		return profilePicture;
	}
}
