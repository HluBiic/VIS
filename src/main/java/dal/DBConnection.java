package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import lombok.Getter;

public class DBConnection {
	private static DBConnection instance;
	
	@Getter
	private Connection con;
	
	private DBConnection() {
		try {
			Class.forName("org.h2.Driver");//for Tomcat
			//this.con = DriverManager.getConnection("jdbc:h2:file:./database/r6sDB;AUTO_SERVER=TRUE");
			//String dbPath = System.getProperty("DB_PATH", "D:/Users/hlubi/JAVA II/r6matches/database/r6sDB");
			
			// Use "~/" which H2 understands as "Current User's Home Directory"
	        // This creates the DB at: C:\Users\YourUser\r6s_matches_db
	        String dbPath = "~/r6s_matches_db";
			this.con = DriverManager.getConnection("jdbc:h2:file:" + dbPath + ";AUTO_SERVER=TRUE");
		} catch (SQLException | ClassNotFoundException e) {
			throw new RuntimeException("DB connection error: " + e.getMessage());
		}
	}
	
	public static DBConnection getInstance() throws SQLException {
		if (instance == null) {
			instance = new DBConnection();
		}
		return instance;
	}
}
