package dal.repo;

import java.util.ArrayList;
import java.util.List;

import dal.gateway.MapGateway;
import dto.MapDTO;

public class MapRepository {

	private MapGateway mapGateway = new MapGateway();
	
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
	
	public MapDTO insert(String name) {
		return mapGateway.insert(name);
	}
	
	public MapDTO update(MapDTO map) {
		return mapGateway.update(map);
	}
	
	public MapDTO delete(int id) {
		return mapGateway.delete(id);
	}
	
	public MapDTO findById(int id) {
		return mapGateway.findById(id);
	}
	
	public List<MapDTO> findAll() {
		return mapGateway.findAll();
	}
	
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
