package dto;

/**
 * Factory class for generating all available DTO objects.
 */
public class AllDTOFactory {

	/**
	 * Factory method to generate a Map DTO object.
	 * @param id id of the map
	 * @param name name of the map
	 * @return created Map DTO object with passed parameters
	 */
	public static MapDTO createMap(int id, String name) {
		return new MapDTO(id, name);
	}
	
	/**
	 * Factory method to generate a Match DTO object.
	 * @param id id of the match
	 * @param tournament id of the tournament the match is played on
	 * @param teamA id of the first team
	 * @param teamB id of the second team
	 * @param map id of the map the match is played on
	 * @param score reslut of the match in format [teamA points-teamB points]
	 * @return created Match DTO object with passed parameters
	 */
	public static MatchDTO createMatch(int id, int tournament, int teamA, int teamB, int map, String score) {
		return new MatchDTO(id, tournament, teamA, teamB, map, score);
	}
	
	/**
	 * Factory method to generate a Tournament DTO object.
	 * @param id id of the tournament
	 * @param name name of the tournament
	 * @param location location where the tournament was played
	 * @param date date when the tournament was played
	 * @return created Tournament DTO object with passed parameters
	 */
	public static TournamentDTO createTournament(int id, String name, String location, String date) {
		return new TournamentDTO(id, name, location, date);
	}
	
	/**
	 * Factory method to generate a Team DTO object.
	 * @param id id of the team
	 * @param name name of the team
	 * @param region region of the team
	 * @return created Team DTO object with passed parameters
	 */
	public static TeamDTO createTeam(int id, String name, String region) {
		return new TeamDTO(id, name, region);
	}
}