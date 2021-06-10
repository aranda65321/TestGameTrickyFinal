package Controller;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;

import Model.DataBaseUtils;

public class CtrIndex extends SelectorComposer<Component> {
	
	   private static final long serialVersionUID = -7862676446685085257L;
	   private String dateH;
	   private int id;
	   @Wire
	   private Textbox play1;
	   @Wire
	   private Textbox play2;
	   @Wire
	   private Textbox bSize;
	   @Wire
	   private Button startButton;

	   public void doAfterCompose(Component comp) throws Exception {
	      super.doAfterCompose(comp);
	   }

	   @Listen("onClick = #startButton")
	   public void startButton(Event ev) throws SQLException, ClassNotFoundException {
	      if ((play1.getValue() != "") && (play2.getValue() != "") && (bSize.getValue() != "")) {
	         DataBaseUtils utils = new DataBaseUtils();
	         Date date = new Date();
	         DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
	         dateH = format.format(date);
	         id = GenerateId();
	         if (utils.Creategame(play1.getValue(), play2.getValue(), "none", id, dateH)) {
	            InfoGame(this.play1.getValue(), play2.getValue(),bSize.getValue(), id);
	            Executions.sendRedirect("/dashboard.zul");
	         } else {
	            alert("Game not created");
	         }
	      }
	   }

	   public int GenerateId() {
	      int number = 0;
	      boolean correctId = false;
	      DataBaseUtils utils = new DataBaseUtils();
	      while(correctId == false) {
	    	  Random random = new Random();
	    	  number = random.nextInt(1000);
	 	      correctId = utils.FindId(number);
	      }
	      System.out.print(number);
	      return number;
	   }

	   public void InfoGame(String play1, String play2, String bsize, int id) {
	      Session session = Sessions.getCurrent();
	      session.setAttribute("player1", play1);
	      session.setAttribute("player2", play2);
	      session.setAttribute("bSize", bsize);
	      session.setAttribute("idG", String.valueOf(id));
	   }
}
