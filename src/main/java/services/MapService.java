package services;

import java.util.List;

import dal.repo.MapRepository;
import dto.MapDTO;

public class MapService {

	private final MapRepository mapRepo = new MapRepository();
	
	public List<MapDTO> getAllMaps() {
		return mapRepo.findAll();
	}
}
