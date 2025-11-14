package dto;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class AllDTOFactory {

	public static MapDTO createMap(int id, String name) {
		//validation + logging
		log.info("MapDTO CREATED: [" + id + "] - " + name);
		return new MapDTO(id, name);
	}
	
	//we dont need to log but want to use factory in gateway for consistency
	public static MapDTO loadMap(int id, String name) {
		//log.info("MapDTO LOADED from DB - new DTO created: [" + id + "] - " + name);
		return new MapDTO(id, name);
	}
	
	public static MatchDTO createMatch(int id, int map, String score) {
		return new MatchDTO(id, map, score);
	}
}
