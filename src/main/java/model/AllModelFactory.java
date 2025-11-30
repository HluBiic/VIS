package model;

public class AllModelFactory {
	public static Map createMap(int id, String name) {
		return new Map(id, name);
	}
	
	public static Match createMatch(int id, Map map, String score) {
		return new Match(id, map, score);
	}
}
