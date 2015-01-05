package br.org.tutty.collaborative_whiteboard.transmition.services;;import br.org.tutty.collaborative_whiteboard.cw.context.SelectedDataContext;
import br.org.tutty.collaborative_whiteboard.cw.context.UserContext;
import br.org.tutty.collaborative_whiteboard.cw.model.Project;
import br.org.tutty.collaborative_whiteboard.transmition.context.TransmitionContext;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpSession;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;

@RunWith(MockitoJUnitRunner.class)
public class TransmitionsServiceBeanTest{

    private static final String IDENTIFICATION_CODE = "888-888-888-888";
    @InjectMocks private TransmitionsServiceBean transmitionsServiceBean;
    @Mock private UserContext userContext;
    @Mock private SelectedDataContext selectedDataContext;
    @Mock private TransmitionContext transmitionContext;
    @Mock private Project selectedProject;
    @Mock private Session socketSessionSender;
    @Mock private HttpSession httpSession;
    @Mock private RemoteEndpoint.Basic basicRemote;

    @Test
    public void connectShouldGetProjectIdentificationCode() throws SelectedDataContext.UnselectedException {
        Mockito.when(selectedDataContext.getSelectedProject()).thenReturn(selectedProject);
        Mockito.when(selectedProject.getIdentificationCode()).thenReturn(IDENTIFICATION_CODE);
        Mockito.when(socketSessionSender.getBasicRemote()).thenReturn(basicRemote);

        transmitionsServiceBean.connect(socketSessionSender, httpSession);

        Mockito.verify(selectedDataContext).getSelectedProject();
    }

    @Test
    public void connectShouldStartTransmition(){
        Assert.fail();
    }

    @Test
    public void connectShouldSendOnlineMessageWhenTransmitionInit(){
        Assert.fail();
    }

    @Test
    public void connectShouldSendOfflineMessageWhenThrowConnectException(){
        Assert.fail();
    }

    @Test
    public void connectShouldSendOfflineMessageWhenThrowUnselectedException(){
        Assert.fail();
    }
}