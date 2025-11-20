package dal.gateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dal.DBConnection;
import dto.AllDTOFactory;
import dto.MapDTO;
import dto.MatchDTO;
import dto.TournamentDTO;
import lombok.extern.log4j.Log4j2;

//will wrap direct work with DB and provide CRUD and return DTO objects
@Log4j2
public class TournamentGateway {
	private Connection con;
	
	//initialization -> creating table
	public TournamentGateway() {
		try {
			this.con = DBConnection.getInstance().getCon();
			Statement s = con.createStatement();
			s.execute("CREATE TABLE IF NOT EXISTS tournaments ("
					+ "id INT AUTO_INCREMENT PRIMARY KEY,"
					+ "name VARCHAR(50),"
					+ "location VARCHAR(50),"
					+ "date VARCHAR(50))");
			s.close();
			//log.info("Tournament table created.");
		} catch (SQLException e) {
			throw new RuntimeException ("Table nitialization failed: " + e.getMessage());
		}
	}
	
	//C from CRUD
	public TournamentDTO insert(String name, String location, String date) {
		try (PreparedStatement ps = this.con.prepareStatement(
				"INSERT INTO tournaments (name, location, date) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
			
			ps.setString(1, name);
			ps.setString(2, location);
			ps.setString(3, date);
			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int generatedID = rs.getInt(1);
				log.info("Tournament [" + generatedID + "] - " + name + " inserted into DB.");
				return AllDTOFactory.createTournament(generatedID, name, location, date);
			}
			
		} catch (SQLException e) {
			log.error("Insert failed: " + e.getMessage());
		}
		return null;
	}
	
	
	//R from CRUD
	public TournamentDTO findById(int id) {
		String sql = "SELECT * from tournaments WHERE id = ?";
		try (PreparedStatement ps = this.con.prepareStatement(sql)) {
			ps.setInt(1, id);
			
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return AllDTOFactory.createTournament(
							rs.getInt("id"),
							rs.getString("name"),
							rs.getString("location"),
							rs.getString("date"));
				}
			}
		} catch (SQLException e) {
			 log.error("FindById failed: " + e.getMessage());
		}
		return null;		
	}
	
	
	//R from CRUD
	public List<TournamentDTO> findAll() {
		List<TournamentDTO> tournaments = new ArrayList<TournamentDTO>();
		String sql = "SELECT * FROM tournaments";
		
		try (Statement s = this.con.createStatement();
			 ResultSet rs = s.executeQuery(sql)) {
			
			while (rs.next()) {
				tournaments.add(AllDTOFactory.createTournament(
						rs.getInt("id"), 
						rs.getString("name"),
						rs.getString("location"),
						rs.getString("date")
				));
			}
		} catch (SQLException e) {
			log.error("FindAll failed: " + e.getMessage());
		}
		return tournaments;
	}
	
	
	//U from CRUD
	public TournamentDTO update(TournamentDTO t) {
		String sql = "UPDATE tournaments SET name = ?, location = ?, date = ? WHERE id = ?";
		try (PreparedStatement ps = this.con.prepareStatement(sql)) {
			ps.setString(1, t.getName());
			ps.setString(2, t.getLocation());
			ps.setString(3, t.getDate());
			ps.setInt(4, t.getId());
			
	        int affectedRows = ps.executeUpdate();
	        
	        if (affectedRows > 0) {
	        	log.info("Tournament [" + t.getId() + "] - " + t.getName() + " updated in DB.");
	        	return AllDTOFactory.createTournament(t.getId(), t.getName(), t.getLocation(), t.getDate());
	        }
		} catch (SQLException e) {
			log.error("Update failed: " + e.getMessage());
		}
		return null;
	}
	
	
	//D from CRUD
	public TournamentDTO delete(int id) {
		TournamentDTO t = this.findById(id);
		String sql = "DELETE FROM tournaments WHERE id = ?";
		try(PreparedStatement ps = this.con.prepareStatement(sql)) {
			ps.setInt(1, id);
			
			int affectedRows = ps.executeUpdate();
			
			if (affectedRows > 0) {
				log.info("Tournament [" + t.getId() + "] deleted in DB");
				return AllDTOFactory.createTournament(t.getId(), t.getName(), t.getLocation(), t.getDate());
			}
		} catch (SQLException e) {
			log.error("Delete failed: " + e.getMessage());
		}
		return null;
	}
}
