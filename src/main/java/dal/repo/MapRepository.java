package dal.repo;

import java.util.List;

import dal.gateway.MapGateway;
import dto.MapDTO;

public class MapRepository {

	private static MapGateway mapGateway = new MapGateway();
	
	public static MapDTO insert(String name) {
		return mapGateway.insert(name);
	}
	
	public static MapDTO findById(int id) {
		return mapGateway.findById(id);
	}
	
	public static List<MapDTO> findAll() {
		return mapGateway.findAll();
	}
	
}
