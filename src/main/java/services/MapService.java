package services;

import java.util.ArrayList;
import java.util.List;

import app.utilities.Listener;
import dal.EntityKind;
import dal.UnitOfWork;
import dal.repo.MapRepository;
import dto.AllDTOFactory;
import dto.MapDTO;
import mapper.Mapper;
import model.AllModelFactory;
import model.Map;

public class MapService {

	private final MapRepository mapRepo = new MapRepository();
	
	//will only insert the map if it doesnt already exist in DB
	public void initMaps() {
		mapRepo.init();
	}
	
	public Map newMap(String name, String caster) {
		UnitOfWork uow = new UnitOfWork();
		try {
			uow.begin();
			MapDTO dto = AllDTOFactory.createMap(0, name);
			uow.registerNew(dto);
			uow.commit();
			Map m = Mapper.toDomain(dto);			
			return m;
		} catch (Exception e) {
			throw new RuntimeException("Map creation failed: " + e.getMessage());
		}
	}
	
	public List<Map> getAllMaps() {
		List<Map> result = new ArrayList<>();
		List<MapDTO> maps = mapRepo.findAll();
		for ( MapDTO m : maps) {
			result.add(Mapper.toDomain(m));
		}
		return result;
	}
	
	public Map getMapByName(String name) {
		return Mapper.toDomain(mapRepo.findByName(name));
	}
}
