package dal.repo;

import java.util.ArrayList;
import java.util.List;

import dal.gateway.MapGateway;
import dto.MapDTO;

public class MapRepository {

	private MapGateway mapGateway = new MapGateway();
	
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
}
