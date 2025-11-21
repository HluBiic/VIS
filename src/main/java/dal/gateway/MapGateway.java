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

/**
 * Class which wraps a direct work with the database, provides CRUD operations
 * and returns DTO objects.
 */
@Log4j2
public class MapGateway {
	private Connection con;
	
	/**
	 * Constructor which creates a database table for Map if it doesnt exist.
	 */
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

	/**
	 * Method which inserts new map into database (C - CRUD).
	 * @param name name of the map
	 * @return new map object which was inserted into database
	 */
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
	
	/**
	 * Method which looks for existing map in the database by its id (R - CRUD).
	 * @param id id of the map to look for
	 * @return if the map with given id exists in database this object is returned
	 */
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
	
	/**
	 * Method which lists all existing maps in the database (R - CRUD).
	 * @return list of all maps that exist in the database
	 */
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
	
	/**
	 * Method which updates a given map with new data in the database (U - CRUD).
	 * @param map map object containing the new data to be updated
	 * @return updated map object if the update was successfull
	 */
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
	
	/**
	 * Method which deletes a given map from the database (D - CRUD).
	 * @param id id of the map to delete
	 * @return the deleted map object from the database if it exists
	 */
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