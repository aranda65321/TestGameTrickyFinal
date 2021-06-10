package Controller;


import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import Model.DataBaseUtils;


public class CtrDashboard extends SelectorComposer<Component>{

	private static final long serialVersionUID = -1962206707232706558L;
	private int [][] grid;
	private int turn = 1;
	private int count =0;
	private String player1;
	private String player2;
	private String size, ids;
	private int bSize;
	private int id;
	@Wire
	private Grid gridGame;
	@Wire
	private Grid record;
	@Wire
	private Button reset;
	@Wire
	private Button exit;
	
	public CtrDashboard() {
	}

	public CtrDashboard(int turn, String player1, String player2, int bSize, int id) {
		super();
		this.turn = turn;
		this.player1 = player1;
		this.player2 = player2;
		this.bSize = bSize;
		this.id = id;
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		DataBaseUtils showGrid = new DataBaseUtils();
		Session session = Sessions.getCurrent();
		player1 = (String) session.getAttribute("player1");
		player2 = (String) session.getAttribute("player2");
		size = (String) session.getAttribute("bSize");
		ids = (String) session.getAttribute("idG");
		if((player1 == null) || (player2 == null) || (size == null) || (ids == null)) {
			Executions.sendRedirect("/index.zul");	
		}else {
		bSize = Integer.parseInt(size);
		id = Integer.parseInt(ids);
		grid = new int[bSize][bSize];
		GenerateGrid(bSize);
		showGrid.startShow(record);
		}
	}
	
	public void GenerateGrid(int size) {
		Rows rows = gridGame.getRows();
		for (int i = 0; i < size; i++) {
			Row row = new Row();
			rows.setStyle("width=80px");
			for (int j = 0; j < size; j++) {
				Button button =  new Button();
				button.setSclass("bt_game");
				button.setId(""+i+"-"+j+"");
				button.setLabel("-");
				row.appendChild(button);
				button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event arg0) {
					ChangeParameters(button,turn);	
					CalculateWinner(grid, size, button);
				}
				});
			}
			rows.appendChild(row);
		}
	}
	
	public void ChangeParameters(Button id, int turn) {
		if(turn==1) {
		id.setLabel("X");
		id.setDisabled(true);
		saveposition(id, turn);
		this.turn=2;
		}
		if(turn==2) {
		id.setLabel("O");
		saveposition(id, turn);
		id.setDisabled(true);
		
		this.turn=1;
		}
	}
	
	public void saveposition(Button id, int turn) {
		String spli[] = id.getId().split("-");
		if(turn==1) {
			grid[Integer.parseInt(spli[0])][Integer.parseInt(spli[1])] = 1;
		}
		if(turn==2) {
			grid[Integer.parseInt(spli[0])][Integer.parseInt(spli[1])] = 2;
		}
	}
	
	public void CalculateWinner(int pos [][],int bsize, Button button) {
		String spli[] = button.getId().split("-");
		int indexR= Integer.parseInt(spli[0]);
		int indexC= Integer.parseInt(spli[1]);
		int countP1 =0;
		int countP2 =pos.length;
        String row1 = "";
        String row2 = "";
        String auxR = "";
        String auxC = "";
        String auxD1 = "";
        String auxD2 = "";
        count++;
  
        for (int i = 0; i < bsize; i++) {
        	row1 += "1";
        	row2 += "2";            
		}
      //validate rows, columns and diagonals
		for (int j = 0; j < pos.length; j++) {
			auxR += String.valueOf(pos[indexR][j]);
		}
		for (int j = 0; j < pos.length; j++) {
			auxC += String.valueOf(pos[j][indexC]);
		}
		for (int k = 0; k < pos.length; k++) {
			auxD1 += String.valueOf(pos[countP1][k]);
			countP1++;
		}
		for (int m = 0; m < pos.length; m++) {
			auxD2 += String.valueOf(pos[m][countP2-1]);
			countP2--;
		}
		if((auxR.equals(row1)) || (auxC.equals(row1)) || (auxD1.equals(row1)) || (auxD2.equals(row1))) {
			alert("ganador jugador: " + player1);
			SaveWinner(player1);
		}
		if(auxR.equals(row2) || auxC.equals(row2) || auxD1.equals(row2) || auxD2.equals(row2)) {
			alert("ganador jugador: " + player2 );
			SaveWinner(player2);
		}
		if(count == (bsize*bsize-1) && (bSize>2)) {
			alert("Empate");
			SaveWinner("Empate");
		}
	}
	
	public void SaveWinner (String player12) {
		DataBaseUtils update = new DataBaseUtils();
		update.UpdateWinner(id, player12);
	} 
	
	@Listen("onClick = #reset")
	public void resetGame() {
		turn = 1;
		count =0;		
		Executions.sendRedirect("/dashboard.zul");	
	}
	
	@Listen("onClick = #exit")
	public void exitGame() {
		Session session = Sessions.getCurrent();
		session.removeAttribute(player1);
		session.removeAttribute(player2);
		session.removeAttribute(String.valueOf(bSize));
		session.removeAttribute(String.valueOf(id));
		Executions.sendRedirect("/index.zul");			
		
	}
}
