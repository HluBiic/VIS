package mapper;

import dal.repo.MapRepository;
import dto.AllDTOFactory;
import dto.MapDTO;
import dto.MatchDTO;
import model.AllModelFactory;
import model.Match;
import model.Map;

public class Mapper {

	private static final MapRepository mapRepo = new MapRepository();

	
	//Domain -> DTO
	public static MatchDTO toDto(Match m) {
		return AllDTOFactory.createMatch(m.getId(), m.getMap().getId(), m.getScore());
	}
	
	//DTO -> Domain
	public static Match toDomain(MatchDTO m) {
		MapDTO mapDTO = mapRepo.findById(m.getMap());
		Map map = AllModelFactory.createMap(mapDTO.getId(), mapDTO.getName());
		return AllModelFactory.createMatch(m.getId(), map, m.getScore());
	}
	
	public static MapDTO toDto(Map m) {
		return AllDTOFactory.createMap(m.getId(), m.getName());
	}
	
	public static Map toDomain(MapDTO m) {
		return AllModelFactory.createMap(m.getId(), m.getName());
	}
}

