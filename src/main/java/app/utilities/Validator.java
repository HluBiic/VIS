package app.utilities;

import java.util.List;

import model.Map;
import model.Team;
import model.Tournament;
import services.MapService;
import services.TeamService;
import services.TournamentService;

/**
 * Provides methods for validating user input fields against business rules and existing data.
 */
public class Validator {
	private static MapService mapService = new MapService();
	private static TournamentService tourService = new TournamentService();
	private static TeamService teamService = new TeamService();

	/**
	 * Private constructor to prevent instantiation of the validator class.
	 */
	private Validator() {}
	
	/**
	 * Validates if the given team A name is non-empty and exists in the database.
	 */
	public static String validateTeamAName(String name) {
		if (name.isEmpty()) {
			return "Názov tímu je povinné pole.\n"
					+ "Chyba nájdená v poli: [2. Zadajte tím A: ].";
		}
		
		List<Team> teams = teamService.getAllTeams();
		boolean teamExists = false;
		for (Team t : teams) {
			if (t.getName().equals(name)) {
				teamExists = true;
				return null;
			}
		}
		
		if (!teamExists) {
			return "Tento tím neexistuje v DB. Prosím zadajte existujúci tím.\n"
					+ "Chyba nájdená v poli: [2. Zadajte tím A: ].";
		}
		return null;
	}
	
	/**
	 * Validates if the given team B name is non-empty and exists in the database.
	 */
	public static String validateTeamBName(String name) {
		if (name.isEmpty()) {
			return "Názov tímu je povinné pole.\n"
					+ "Chyba nájdená v poli: [3. Zadajte tím B: ].";
		}
		
		List<Team> teams = teamService.getAllTeams();
		boolean teamExists = false;
		for (Team t : teams) {
			if (t.getName().equals(name)) {
				teamExists = true;
				return null;
			}
		}
		
		if (!teamExists) {
			return "Tento tím neexistuje v DB. Prosím zadajte existujúci tím.\n"
					+ "Chyba nájdená v poli: [3. Zadajte tím B: ].";
		}
		return null;
	}
	
	/**
	 * Validates that team A and team B names are different (a team cannot play against itself).
	 */
	public static String validateTeamVsTeam(String teamA, String teamB) {
		if (teamA.equals(teamB)) {
			return "Tím nemôže hrať sám so sebou. Prosím zadajte 2 rozdielne tímy.\n"
					+ "Chyba nájdená v poliach: [2. Zadajte tím A: ][3. Zadajte tím B: ].";
		}
		return null;
	}
	
	/**
	 * Validates if the given tournament name is non-empty and exists in the database.
	 */
	public static String validateTournamentName(String name) {
		if (name.isEmpty()) {
			return "Názov turnaja je povinné pole.\n"
					+ "Chyba nájdená v poli: [1. Zadajte turnaj: ].";
		}
		
		List<Tournament> tours = tourService.getAllTournaments();
		boolean tourExists = false;
		for (Tournament t : tours) {
			if (t.getName().equals(name)) {
				tourExists = true;
				return null;
			}
		}
		
		if (!tourExists) {
			return "Tento turnaj neexistuje v DB. Prosím zadajte existujúci turnaj.\n"
					+ "Chyba nájdená v poli: [1. Zadajte turnaj: ].";
		}
		return null;
	}
	
	/**
	 * Validates if the given map name is non-empty and exists in the database.
	 */
	public static String validateMapName(String name) {
		if (name.isEmpty()) {
			return "Názov mapy je povinné pole.\n"
					+ "Chyba nájdená v poli: [5. Zadajte názov mapy: ].";
		}
		
		List<Map> maps = mapService.getAllMaps();
		boolean mapExists = false;
		for (Map m : maps) {
			if (m.getName().equals(name)) {
				mapExists = true;
				return null;
			}
		}
		
		if (!mapExists) {
			return "Tato mapa neexistuje v DB. Prosím zadajte existujúcu mapu.\n"
					+ "Chyba nájdená v poli: [5. Zadajte názov mapy: ].";
		}
		return null;
	}
	
	/**
	 * Validates if the match score string is in the required A-B format.
	 */
	public static String validateMatchScore(String score) {
		if (!score.matches("\\d{1,2}-\\d{1,2}")) {
			return "Neplatný formát skóre. Skóre musí byť v tvare A-B (napr. 7-5 alebo 8-7).\n"
					+ "Chyba nájdená v poli: [4. Zadajte skóre: ]";
		}
		return null;
	}
}