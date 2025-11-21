package services;

import java.util.ArrayList;
import java.util.List;

import dal.UnitOfWork;
import dal.repo.MapRepository;
import dto.AllDTOFactory;
import dto.MapDTO;
import mapper.Mapper;
import model.Map;

/**
 * Provides service layer operations for managing Map entities, acting as an intermediary
 * between the presentation/application layer and the data access layer (MapRepository).
 */
public class MapService {

	private final MapRepository mapRepo = new MapRepository();
	
	/**
	 * Initializes the database with a default set of maps if they do not already exist.
	 */
	public void initMaps() {
		mapRepo.init();
	}
	
	/**
	 * Creates a new Map entity, registers it with the Unit of Work, and commits the transaction.
	 */
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
	
	/**
	 * Retrieves all maps from the database and converts them into domain models.
	 */
	public List<Map> getAllMaps() {
		List<Map> result = new ArrayList<>();
		List<MapDTO> maps = mapRepo.findAll();
		for ( MapDTO m : maps) {
			result.add(Mapper.toDomain(m));
		}
		return result;
	}
	
	/**
	 * Retrieves a single Map entity based on its name and converts it to a domain model.
	 */
	public Map getMapByName(String name) {
		return Mapper.toDomain(mapRepo.findByName(name));
	}
}