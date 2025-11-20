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
import dto.TeamDTO;
import dto.TournamentDTO;
import lombok.extern.log4j.Log4j2;

//will wrap direct work with DB and provide CRUD and return DTO objects
@Log4j2
public class TeamGateway {
	private Connection con;
	
	//initialization -> creating table
	public TeamGateway() {
		try {
			this.con = DBConnection.getInstance().getCon();
			Statement s = con.createStatement();
			s.execute("CREATE TABLE IF NOT EXISTS teams ("
					+ "id INT AUTO_INCREMENT PRIMARY KEY,"
					+ "name VARCHAR(50),"
					+ "region VARCHAR(50))");
			s.close();
			//log.info("Team table created.");
		} catch (SQLException e) {
			throw new RuntimeException ("Table nitialization failed: " + e.getMessage());
		}
	}
	
	//C from CRUD
	public TeamDTO insert(String name, String region) {
		try (PreparedStatement ps = this.con.prepareStatement(
				"INSERT INTO teams (name, region) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
			
			ps.setString(1, name);
			ps.setString(2, region);
			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int generatedID = rs.getInt(1);
				log.info("Team [" + generatedID + "] - " + name + " inserted into DB.");
				return AllDTOFactory.createTeam(generatedID, name, region);
			}
			
		} catch (SQLException e) {
			log.error("Insert failed: " + e.getMessage());
		}
		return null;
	}
	
	//R from CRUD
	public TeamDTO findById(int id) {
		String sql = "SELECT * from teams WHERE id = ?";
		try (PreparedStatement ps = this.con.prepareStatement(sql)) {
			ps.setInt(1, id);
			
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return AllDTOFactory.createTeam(
							rs.getInt("id"),
							rs.getString("name"),
							rs.getString("region"));
				}
			}
		} catch (SQLException e) {
			 log.error("FindById failed: " + e.getMessage());
		}
		return null;		
	}
	
	//R from CRUD
	public List<TeamDTO> findAll() {
		List<TeamDTO> teams = new ArrayList<TeamDTO>();
		String sql = "SELECT * FROM teams";
		
		try (Statement s = this.con.createStatement();
			 ResultSet rs = s.executeQuery(sql)) {
			
			while (rs.next()) {
				teams.add(AllDTOFactory.createTeam(
						rs.getInt("id"), 
						rs.getString("name"), 
						rs.getString("region")
				));
				
			}
		} catch (SQLException e) {
			log.error("FindAll failed: " + e.getMessage());
		}
		return teams;
	}
	
	//U from CRUD
	public TeamDTO update(TeamDTO t) {
		String sql = "UPDATE teams SET name = ?, region = ? WHERE id = ?";
		try (PreparedStatement ps = this.con.prepareStatement(sql)) {
			ps.setString(1, t.getName());
			ps.setString(2, t.getRegion());
			ps.setInt(3, t.getId());
			
	        int affectedRows = ps.executeUpdate();
	        
	        if (affectedRows > 0) {
	        	log.info("Team [" + t.getId() + "] - " + t.getName() + " updated in DB.");
	        	return AllDTOFactory.createTeam(t.getId(), t.getName(), t.getRegion());
	        }
		} catch (SQLException e) {
			log.error("Update failed: " + e.getMessage());
		}
		return null;
	}
	
	//D from CRUD
	public TeamDTO delete(int id) {
		TeamDTO t = this.findById(id);
		String sql = "DELETE FROM teams WHERE id = ?";
		try(PreparedStatement ps = this.con.prepareStatement(sql)) {
			ps.setInt(1, id);
			
			int affectedRows = ps.executeUpdate();
			
			if (affectedRows > 0) {
				log.info("Team [" + t.getId() + "] deleted in DB");
				return AllDTOFactory.createTeam(t.getId(), t.getName(), t.getRegion());
			}
		} catch (SQLException e) {
			log.error("Delete failed: " + e.getMessage());
		}
		return null;
	}
}