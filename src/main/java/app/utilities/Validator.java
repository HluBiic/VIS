package app.utilities;

public class Validator {

	private Validator() {}
	
	public static String validateMapName(String name) {
		if (name.isEmpty()) {
			return "Názov mapy je povinné pole.";
		}
		return null;
	}
	
	public static String validateMatchScore(String score) {
		if (!score.matches("\\d{1,2}-\\d{1,2}")) {
			return "Neplatný formát skóre. Skóre musí byť v tvare A-B (napr. 7-5 alebo 8-7).";
		}
		return null;
	}
	
	
}
