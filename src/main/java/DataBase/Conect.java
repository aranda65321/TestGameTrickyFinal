package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conect {

	   private static final String jdbDriver = "org.h2.Driver";
	   private static final String dbUrl = "jdbc:h2:file:C:/Users/Pc/eclipse-workspace/GameTricky/src/main/java/DataBase/Database";
	   private static final String user = "sa";
	   private static final String pass = "";
	   private Connection conn;

	   public Conect() {
	      this.conn = null;
	   }

	   public Conect(Connection conn) {
		  super();
	      this.conn = conn;
	   }

	   public Connection Conectdatabase() {
	      try {
	         Class.forName(jdbDriver);
	         conn = DriverManager.getConnection(dbUrl,user, pass);
	      }catch(SQLException se) { 
	          se.printStackTrace(); 
	       } catch(Exception e) { 
	          e.printStackTrace(); 
	       } 
	       return conn;
	   }
}
