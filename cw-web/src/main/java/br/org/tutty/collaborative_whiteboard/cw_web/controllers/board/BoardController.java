package br.org.tutty.collaborative_whiteboard.cw_web.controllers.board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.DragDropEvent;

@ManagedBean(name = "boardController")
@ViewScoped
public class BoardController implements Serializable {

	private static final long serialVersionUID = -2434188074137226652L;

	private ArrayList<BoardColumn> columns;

	public List<BoardColumn> getColumns() {
		columns = new ArrayList<>();
		ArrayList<String> tickets = new ArrayList<>();

		tickets.add("T1");
		tickets.add("T2");
		columns.add(new BoardColumn("todo", "A Fazer", tickets));

		tickets = new ArrayList<>();
		tickets.add("T3");
		tickets.add("T4");
		columns.add(new BoardColumn("doing", "Fazendo", tickets));

		tickets = new ArrayList<>();
		tickets.add("T5");
		tickets.add("T6");
		columns.add(new BoardColumn("done", "Feito", tickets));

		tickets = new ArrayList<>();
		tickets.add("T7");
		tickets.add("T8");
		columns.add(new BoardColumn("accepted", "Aceito", tickets));

		return columns;
	}

	public void onTicketDrop(DragDropEvent event) {
		String ticket = (String) event.getData();
		StringBuilder builder = new StringBuilder(ticket);
		builder.append(" foi da coluna ");
		builder.append(" ");
		builder.append(event.getDragId());
		builder.append(" ");
		builder.append(event.getDropId());
		System.out.println(builder.toString());
	}

}
