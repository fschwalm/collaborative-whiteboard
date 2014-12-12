package br.org.tutty.collaborative_whiteboard.context;

import javax.ejb.Local;
import javax.websocket.Session;

import org.json.JSONException;
import org.json.JSONObject;

@Local(BroadCastService.class)
public class BroadCastServiceBean implements BroadCastService {

	@Override
	public void updateClients(JSONObject jsonObject, Session session) {
		try {
			jsonObject.putOpt("user", "Usuario");

			session.getOpenSessions().forEach(s -> {
				if (!s.getId().equals(session.getId())) {
					try {
						s.getBasicRemote().sendText(jsonObject.toString());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (JSONException e) {
			e.printStackTrace();
		}

		// System.out.println("Falha ao atualizar clientes.");
	}

	@Override
	public void updateClients(String message, Session session) {
		try {
			updateClients(new JSONObject(message), session);
		} catch (JSONException e) {
			System.out.println("Falha ao converter dado para JSON.");
		}
	}

}
