package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import DataBase.Conect;

public class DataBaseUtils {
	
   private ResultSet rs;
   private Statement stmt;
   private ArrayList<Game> userData = new ArrayList<Game>();
   Conect connect = new Conect();
   
   public DataBaseUtils() {
	   this.rs = null;
	   this.stmt=null;
	}

   public boolean Creategame(String play1, String play2, String winner, int id, String date) {
      boolean finish = false;
      int rs = 0;
      Connection con = connect.Conectdatabase();
      try {
         PreparedStatement st = con.prepareStatement("INSERT INTO GAME.PLAYERS VALUES('" + play1 + "','" + play2 + "','" + winner + "','" + id + "','" + date + "');");
         rs = st.executeUpdate();
         st.close();
         con.close();
         finish = true;
      } catch (SQLException sQ) {
         finish = false;
      }
      return finish;
   }

   public boolean FindId(int id) {
      boolean idFound = false;
      Connection con = connect.Conectdatabase();
      try {
         PreparedStatement st = con.prepareStatement("SELECT * FROM GAME.PLAYERS WHERE ID = '" + id + "';");
         rs = st.executeQuery();
         rs.next();
         idFound = true;
         st.close();
         con.close();
      	} catch (Exception ex) {
         idFound = false;
      	}
      return idFound;
   	}
   
   public void UpdateWinner(int id, String name) {
	      String sql;
	      Connection con = connect.Conectdatabase();
	      try {
	    	 stmt = con.createStatement(); 
	         sql = "UPDATE GAME.PLAYERS SET WINNER = '"+name+"' WHERE ID = " + id + ";";
	         System.out.print(sql);
	         stmt.executeUpdate(sql);
	         stmt.close();
	         con.close();
	      	} catch (Exception ex) {
	      	}
	   	}
   
   public void startShow(Grid list_data) {
	   Connection con = connect.Conectdatabase();	
	   Game gameAux;
		try {
			PreparedStatement st = con.prepareStatement("SELECT * FROM GAME.PLAYERS;");
			rs = st.executeQuery();
			Rows rows = list_data.getRows();
			while(rs.next()) {
				Game game = new Game();
				game.setNamePlayer1(rs.getString("PLAYER1"));
				game.setNamePlayer2(rs.getString("PLAYER2"));
				game.setWinner(rs.getString("WINNER"));
				game.setIdGame(rs.getInt("ID"));
				game.setDate(rs.getString("DATE"));
				userData.add(game);
			}
			for (int i = 0; i < userData.size(); i++) {
				for (int j = 0; j < userData.size()-i-1; j++) {
					String spliS[] = userData.get(j+1).getDate().split("/");
					String spliA[] = userData.get(j).getDate().split("/");
					int dayS = Integer.parseInt(spliS[2]);
					int dayA = Integer.parseInt(spliA[2]);
					if(dayS < dayA) {
						gameAux = userData.get(j+1);
						userData.set(j+1, userData.get(j));
						userData.set(j, gameAux);;
					}
				}
			}
			for (int i = 0; i < userData.size(); i++) {
				Row row = list_Generate(userData.get(i).getNamePlayer1(), userData.get(i).getNamePlayer2(), userData.get(i).getWinner(), userData.get(i).getIdGame(), userData.get(i).getDate());
				rows.appendChild(row);
			}
			st.close();
			con.close();
			} catch (Exception ex) {
		}	
	}
   
	public Row list_Generate(String play1, String play2, String win, int id, String date) {
		Row row = new Row();
		Label textP1 = new Label(play1);
		Label textP2 = new Label(play2);
		Label textW = new Label(win);
		Label textI = new Label(String.valueOf(id));
		Label textD = new Label(date);
		row.appendChild(textP1);
		row.appendChild(textP2);
		row.appendChild(textW);
		row.appendChild(textI);
		row.appendChild(textD);
		return row;
	}
}
