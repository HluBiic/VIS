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
					+ "tour INT NOT NULL,"
					+ "teamA INT NOT NULL,"
					+ "teamB INT NOT NULL,"
					+ "map INT NOT NULL,"
					+ "score VARCHAR(50) NOT NULL)");
			s.close();
			//log.info("Matches table created.");
		} catch (SQLException e) {
			throw new RuntimeException ("Table nitialization failed: " + e.getMessage());
		}
	}
	
	//C from CRUD
	public MatchDTO insert(int tourID, int teamAId, int teamBId, int mapID, String score) {
		try (PreparedStatement ps = this.con.prepareStatement(
				"INSERT INTO matches (tour, teamA, teamB, map, score) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
			
			ps.setInt(1, tourID);
			ps.setInt(2, teamAId);
			ps.setInt(3, teamBId);
			ps.setInt(4, mapID);
			ps.setString(5, score);
			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int generatedID = rs.getInt(1);
				log.info("Match [" + generatedID + "] inserted into DB.");
				return AllDTOFactory.createMatch(generatedID, tourID, teamAId, teamBId, mapID, score);
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
							rs.getInt("tour"),
							rs.getInt("teamA"),
							rs.getInt("teamB"),
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
							rs.getInt("tour"),
							rs.getInt("teamA"),
							rs.getInt("teamB"),
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
		String sql = "UPDATE matches SET tour = ?, teamA = ?, teamB = ?, map = ?, score = ? WHERE id = ?";
		try (PreparedStatement ps = this.con.prepareStatement(sql)) {
			ps.setInt(1, match.getTournament());
			ps.setInt(2, match.getTeamA());
			ps.setInt(3, match.getTeamB());
			ps.setInt(4, match.getMap());
			ps.setString(5, match.getScore());
			ps.setInt(6, match.getId());
			
			int affectedRows = ps.executeUpdate();
			
			if (affectedRows > 0) {
				log.info("Match [" + match.getId() + "] updated in DB");
				return AllDTOFactory.createMatch(match.getId(), match.getTournament(), match.getTeamA(), match.getTeamB(), match.getMap(), match.getScore());
			}
		} catch (SQLException e) {
			log.error("Update failed: " + e.getMessage());
		}
		return null;
	}
	
	//D from CRUD
	public MatchDTO delete(int id) {
		MatchDTO m = this.findById(id);
		String sql = "DELETE FROM matches WHERE id = ?";
		try(PreparedStatement ps = this.con.prepareStatement(sql)) {
			ps.setInt(1, id);
			
			int affectedRows = ps.executeUpdate();
			
			if (affectedRows > 0) {
				log.info("Match [" + m.getId() + "] deleted in DB");
				return AllDTOFactory.createMatch(m.getId(), m.getTournament(), m.getTeamA(), m.getTeamB(), m.getMap(), m.getScore());
			}
		} catch (SQLException e) {
			log.error("Delete failed: " + e.getMessage());
		}
		return null;
	}
}
