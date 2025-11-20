package dto;

import java.time.LocalDate;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class AllDTOFactory {

	public static MapDTO createMap(int id, String name) {
		return new MapDTO(id, name);
	}
	
	public static MatchDTO createMatch(int id, int tournament, int teamA, int teamB, int map, String score) {
		return new MatchDTO(id, tournament, teamA, teamB, map, score);
	}
	
	public static TournamentDTO createTournament(int id, String name, String location, String date) {
		return new TournamentDTO(id, name, location, date);
	}
	
	public static TeamDTO createTeam(int id, String name, String region) {
		return new TeamDTO(id, name, region);
	}
}
