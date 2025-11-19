package dto;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class AllDTOFactory {

	public static MapDTO createMap(int id, String name) {
		return new MapDTO(id, name);
	}
	
	public static MatchDTO createMatch(int id, int map, String score) {
		return new MatchDTO(id, map, score);
	}
}
