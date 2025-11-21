package model;

/**
 * Factory class for generating all available Domain model objects.
 */
public class AllModelFactory {
	/**
	 * Factory method to generate a Map domain model object.
	 * @param id id of the map
	 * @param name name of the map
	 * @return created Map object with passed parameters
	 */
	public static Map createMap(int id, String name) {
		return new Map(id, name);
	}
	
	/**
	 * Factory method to generate a Match domain model object.
     * @param id id of the match
     * @param tournament the tournament the match is played on
     * @param teamA the first team
     * @param teamB the second team
     * @param map id the map the match is played on
     * @param score result of the match in format [teamA points-teamB points] 
	 * @return created Match object with passed parameters
	 */
	public static Match createMatch(int id, Tournament tour, Team teamA, Team teamB, Map map, String score) {
		return new Match(id, tour, teamA, teamB, map, score);
	}
	
	/**
	 * Factory method to generate a Tournament domain model object.
	 * @param id id of the tournament
	 * @param name name of the tournament
	 * @param location location where the tournament was played
	 * @param date date when the tournament was played
	 * @return created Tournament object with passed parameters
	 */
	public static Tournament createTournament(int id, String name, String location, String date) {
		return new Tournament(id, name, location, date);
	}
	
	/**
	 * Factory method to generate a Team domain model object.
	 * @param id is of the team
	 * @param name name of the team
	 * @param region region of the team
	 * @return created Team object with passed parameters
	 */
	public static Team createTeam(int id, String name, String region) {
		return new Team(id, name, region);
	}
}