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
			this.con = DriverManager.getConnection("jdbc:h2:./database/r6sDB");
		} catch (SQLException e) {
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
