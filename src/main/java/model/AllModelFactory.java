package model;

import java.time.LocalDate;

public class AllModelFactory {
	public static Map createMap(int id, String name) {
		return new Map(id, name);
	}
	
	public static Match createMatch(int id, Tournament tour, Team teamA, Team teamB, Map map, String score) {
		return new Match(id, tour, teamA, teamB, map, score);
	}
	
	public static Tournament createTournament(int id, String name, String location, String date) {
		return new Tournament(id, name, location, date);
	}
	
	public static Team createTeam(int id, String name, String region) {
		return new Team(id, name, region);
	}
}
