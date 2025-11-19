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
import dto.MapDTO;
import lombok.extern.log4j.Log4j2;

//will wrap direct work with DB and provide CRUD and return DTO objects
@Log4j2
public class MapGateway {
	private Connection con;
	
	//initialization -> creating table
	public MapGateway() {
		try {
			this.con = DBConnection.getInstance().getCon();
			Statement s = con.createStatement();
			s.execute("CREATE TABLE IF NOT EXISTS maps ("
					+ "id INT AUTO_INCREMENT PRIMARY KEY,"
					+ "name VARCHAR(50))");
			s.close();
			//log.info("Map table created.");
		} catch (SQLException e) {
			throw new RuntimeException ("Table nitialization failed: " + e.getMessage());
		}
	}
	
	//C from CRUD
	public MapDTO insert(String name) {
		try (PreparedStatement ps = this.con.prepareStatement(
				"INSERT INTO maps (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
			
			ps.setString(1, name);
			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int generatedID = rs.getInt(1);
				log.info("Map [" + generatedID + "] - " + name + " inserted into DB.");
				return AllDTOFactory.createMap(generatedID, name);
			}
			
		} catch (SQLException e) {
			log.error("Insert failed: " + e.getMessage());
		}
		return null;
	}
	
	//R from CRUD
	public MapDTO findById(int id) {
		String sql = "SELECT * from maps WHERE id = ?";
		try (PreparedStatement ps = this.con.prepareStatement(sql)) {
			ps.setInt(1, id);
			
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return AllDTOFactory.createMap(
							rs.getInt("id"),
							rs.getString("name"));
				}
			}
		} catch (SQLException e) {
			 log.error("FindById failed: " + e.getMessage());
		}
		return null;		
	}
	
	//R from CRUD
	public List<MapDTO> findAll() {
		List<MapDTO> maps = new ArrayList<MapDTO>();
		String sql = "SELECT * FROM maps";
		
		try (Statement s = this.con.createStatement();
			 ResultSet rs = s.executeQuery(sql)) {
			
			while (rs.next()) {
				maps.add(AllDTOFactory.createMap(
						rs.getInt("id"), 
						rs.getString("name")
				));
			}
		} catch (SQLException e) {
			log.error("FindAll failed: " + e.getMessage());
		}
		return maps;
	}
	
	//U from CRUD
	public MapDTO update(MapDTO map) {
		String sql = "UPDATE maps SET name = ? WHERE id = ?";
		try (PreparedStatement ps = this.con.prepareStatement(sql)) {
			ps.setString(1, map.getName());
			ps.setInt(2, map.getId());
			
	        int affectedRows = ps.executeUpdate();
	        
	        if (affectedRows > 0) {
	        	log.info("Map [" + map.getId() + "] - " + map.getName() + " updated in DB.");
	            return AllDTOFactory.createMap(map.getId(), map.getName());
	        }
		} catch (SQLException e) {
			log.error("Update failed: " + e.getMessage());
		}
		return null;
	}
	
	//D from CRUD
	public MapDTO delete(int id) {
		MapDTO m = this.findById(id);
		String sql = "DELETE FROM maps WHERE id = ?";
		try(PreparedStatement ps = this.con.prepareStatement(sql)) {
			ps.setInt(1, id);
			
			int affectedRows = ps.executeUpdate();
			
	        if (affectedRows > 0) {
	        	log.info("Map [" + m.getId() + "] - " + m.getName() + " deleted from DB.");
	            return AllDTOFactory.createMap(m.getId(), m.getName());
	        }
		} catch (SQLException e) {
			log.error("Delete failed: " + e.getMessage());
		}
		return null;
	}
	
}
