package br.org.tutty.collaborative_whiteboard.cw_web.dtos;

import java.io.Serializable;
import cw.entities.User;
import cw.dtos.LoggedUser;
import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;
import javax.inject.Inject;
import javax.enterprise.context.SessionScoped;
import java.io.ByteArrayInputStream;
import org.primefaces.model.DefaultStreamedContent;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import org.primefaces.model.UploadedFile;
import org.apache.commons.io.IOUtils;
import br.org.tutty.util.PropertyMonitor;
import java.io.IOException;
import javax.inject.Named;

@SessionScoped
@Named
public class ProfilePicture implements Serializable{

	private byte[] profilePicture;

	@Inject
	private SessionContext sessionContext;

	private PropertyMonitor propertyMonitor = new PropertyMonitor(this);

	public void loadPicture(){
		LoggedUser loggedUser = sessionContext.getLoggedUser();
		User user = loggedUser.getUser();
		profilePicture = user.getProfilePicture();
	}

	
	public DefaultStreamedContent getStreamProfilePicture() {
		if(profilePicture != null){
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(profilePicture);
			return new DefaultStreamedContent(new BufferedInputStream(byteArrayInputStream));
		}else {
			return new DefaultStreamedContent();
		}
	}

    public byte[] getProfilePicture(){
        return profilePicture;
    }



	public void setProfilePicture(UploadedFile uploadedFile) {
		try {
			byte[] oldValue = this.profilePicture;

			byte[] bytes = IOUtils.toByteArray(uploadedFile.getInputstream());
			this.profilePicture = bytes;

			propertyMonitor.getPropertyChangeSupport().firePropertyChange("bytes", oldValue, bytes);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
