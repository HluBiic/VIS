package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import lombok.Getter;

/**
 * Singleton class for database connection. The database files
 * are stored at C:\Users\xyz\r6s_matches_db.
 */
public class DBConnection {
	private static DBConnection instance;
	
	@Getter
	private Connection con;
	
	/**
	 * Constructor which sets up the connection to the database.
	 */
	private DBConnection() {
		try {
			Class.forName("org.h2.Driver");
	        String dbPath = "~/r6s_matches_db";
			this.con = DriverManager.getConnection("jdbc:h2:file:" + dbPath + ";AUTO_SERVER=TRUE");
		} catch (SQLException | ClassNotFoundException e) {
			throw new RuntimeException("DB connection error: " + e.getMessage());
		}
	}
	
	/**
     * Provides the single, globally accessible instance of the DBConnection.
     */
	public static DBConnection getInstance() throws SQLException {
		if (instance == null) {
			instance = new DBConnection();
		}
		return instance;
	}
}