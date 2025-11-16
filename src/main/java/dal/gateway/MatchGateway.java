package dal.gateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dal.DBConnection;
import dto.AllDTOFactory;
import dto.MatchDTO;
import lombok.extern.log4j.Log4j2;

//will wrap direct work with DB and provide CRUD and return DTO objects
@Log4j2
public class MatchGateway {
	private Connection con;
	
	//initialization -> creating table
	public MatchGateway() {
		try {
			this.con = DBConnection.getInstance().getCon();
			Statement s = con.createStatement();
			s.execute("CREATE TABLE IF NOT EXISTS matches ("
					+ "id INT AUTO_INCREMENT PRIMARY KEY,"
					+ "map INT NOT NULL,"
					+ "score VARCHAR(50) NOT NULL)");
			s.close();
			log.info("Matches table created.");
		} catch (SQLException e) {
			throw new RuntimeException ("Table nitialization failed: " + e.getMessage());
		}
	}
	
	//C from CRUD
	public MatchDTO insert(int mapID, String score) {
		try (PreparedStatement ps = this.con.prepareStatement(
				"INSERT INTO matches (map, score) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
			
			ps.setInt(1, mapID);
			ps.setString(2, score);
			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int generatedID = rs.getInt(1);
				log.info("Match [" + generatedID + "] inserted into DB.");
				return AllDTOFactory.createMatch(generatedID, mapID, score);
			}
			
		} catch (SQLException e) {
			log.error("Insert failed: " + e.getMessage());
		}
		return null;
	}
	
	//R from CRUD
	public MatchDTO findById(int id) {
		String sql = "SELECT * from matches WHERE id = ?";
		try (PreparedStatement ps = this.con.prepareStatement(sql)) {
			ps.setInt(1, id);
			
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return AllDTOFactory.createMatch(
							rs.getInt("id"), 
							rs.getInt("map"), 
							rs.getString("score"));
				}
			}
		} catch (SQLException e) {
			 log.error("FindById failed: " + e.getMessage());
		}
		return null;		
	}
	
	//R from CRUD
	public List<MatchDTO> findAll() {
		List<MatchDTO> matches = new ArrayList<MatchDTO>();
		String sql = "SELECT * from matches";
		
		try (Statement s = this.con.createStatement();
				 ResultSet rs = s.executeQuery(sql)) {
				
				while (rs.next()) {
					matches.add(AllDTOFactory.createMatch(
							rs.getInt("id"), 
							rs.getInt("map"), 
							rs.getString("score")
					));
				}
			} catch (SQLException e) {
				log.error("FindAll failed: " + e.getMessage());
			}
			return matches;
	}
	
	//U from CRUD
	public MatchDTO update(MatchDTO match) {
		//TODO
		return null;
	}
	
	//D from CRUD
	public MatchDTO delete(int id) {
		//TODO
		return null;
	}
}
