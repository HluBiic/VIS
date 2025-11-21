package dal.repo;

import java.util.ArrayList;
import java.util.List;

import dal.gateway.MapGateway;
import dto.MapDTO;

/**
 * Class which provides higher layer of abstraction on top of gateway classes.
 */
public class MapRepository {

	private MapGateway mapGateway = new MapGateway();
	
	/**
	 * Method which inserts hard coded maps into database if they dont exist.
	 */
	public void init() {
		if (findByName("Bank") == null) {
			mapGateway.insert("Bank");
		}
		if (findByName("Favela") == null) {
			mapGateway.insert("Favela");
		}
		if (findByName("Chalet") == null) {
			mapGateway.insert("Chalet");
		}
		if (findByName("Oregon") == null) {
			mapGateway.insert("Oregon");
		}
	}
	
	/**
	 * Method which inserts map into database through gateway.
	 * @param name name of the map
	 * @return inserted map DTO object
	 */
	public MapDTO insert(String name) {
		return mapGateway.insert(name);
	}
	
	/**
	 * Method which updates map in database through gateway.
	 * @param map map with new data
	 * @return updated map DTO object
	 */
	public MapDTO update(MapDTO map) {
		return mapGateway.update(map);
	}
	
	/**
	 * Method which deletes map from database through gateway.
	 * @param id id of the map to delete
	 * @return deleted map DTO object
	 */
	public MapDTO delete(int id) {
		return mapGateway.delete(id);
	}
	
	/**
	 * Method which finds map by id through gateway.
	 * @param id id of the map to look for
	 * @return map with given id if it exists in database
	 */
	public MapDTO findById(int id) {
		return mapGateway.findById(id);
	}
	
	/**
	 * Method which finds all maps through gateway.
	 * @return list of all maps existing in database
	 */
	public List<MapDTO> findAll() {
		return mapGateway.findAll();
	}
	
	/**
	 * Method which finds maps by given list of ids through gateway
	 * @param ids list of maps ids to look for
	 * @return maps with given ids if they exist in database
	 */
	public List<MapDTO> findByIds(List<Integer> ids) {
		List<MapDTO> results = new ArrayList<MapDTO>();
		for (Integer id : ids) {
			MapDTO m = mapGateway.findById(id);
			if (m != null) {
				results.add(m);
			}
		}
		return results;
	}
	
	/**
	 * Method which finds map by given name through gateway
	 * @param name name of the map to look for
	 * @return map with the given name if it exists in database
	 */
	public MapDTO findByName(String name) {
		List <MapDTO> maps = mapGateway.findAll();
		for (MapDTO m : maps) {
			if (m.getName().equals(name)) {
				return m;
			}
		}
		return null;
	}
}