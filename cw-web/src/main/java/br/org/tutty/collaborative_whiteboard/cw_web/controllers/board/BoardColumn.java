package br.org.tutty.collaborative_whiteboard.cw_web.controllers.board;

import java.util.List;

public class BoardColumn {

	private String id;
	private String title;
	private List<String> tickets;

	public BoardColumn(String id, String title, List<String> tickets) {
		this.id = id;
		this.title = title;
		this.tickets = tickets;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContentId() {
		return id + "-content";
	}

	public String getContentPanelId() {
		return id + "-panel";
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getTickets() {
		return tickets;
	}

	public void setTickets(List<String> tickets) {
		this.tickets = tickets;
	}

}
