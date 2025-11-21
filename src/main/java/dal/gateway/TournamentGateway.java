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
import dto.TournamentDTO;
import lombok.extern.log4j.Log4j2;

/**
 * Class which wraps a direct work with the database, provides CRUD operations
 * and returns DTO objects.
 */
@Log4j2
public class TournamentGateway {
	private Connection con;
	
	/**
	 * Constructor which creates a database table for Tournament if it doesnt exist.
	 */
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
	
	/**
	 * Method which inserts new map into database (C - CRUD).
	 * @param name name of the tournament
	 * @param location location of the tournament
	 * @param date date of the tournament
	 * @return new tournament object which was inserted into database
	 */
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
	
	/**
	 * Method which looks for existing tournament in the database by its id (R - CRUD).
	 * @param id id of the tournament to look for
	 * @return if the tournament with given id exists in database this object is returned
	 */
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
	
	/**
	 * Method which lists all existing tournaments in the database (R - CRUD).
	 * @return list of all tournaments that exist in the database
	 */
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
	
	/**
	 * Method which updates a given tournament with new data in the database (U - CRUD).
	 * @param t tournament object containing the new data to be updated
	 * @return updated tournament object if the update was successfull
	 */
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
	
	/**
	 * Method which deletes a given tournament from the database (D - CRUD).
	 * @param id id of the tournament to delete
	 * @return the deleted tournament object from the database if it exists
	 */
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