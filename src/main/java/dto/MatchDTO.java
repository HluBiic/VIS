package dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO for the Match entity.
 */
@Getter
@Setter
public class MatchDTO {
	private int id;
	private int tournament;
	private int teamA;
	private int teamB;
	private int map;
	private String score;
	
	/**
	 * Constructor of the Match DTO object.
	 * @param id id of the match
	 * @param tournament id of the tournament the match is played on
	 * @param teamA id of the first team
	 * @param teamB id of the second team
	 * @param map id of the map the match is played on
	 * @param score reslut of the match in format [teamA points-teamB points]
	 */
	protected MatchDTO(int id, int tournament, int teamA, int teamB, int map, String score) {
		this.id = id;
		this.tournament = tournament;
		this.teamA = teamA;
		this.teamB = teamB;
		this.map = map;
		this.score = score;
	}

	/**
	 * Basic toString to display object properties.
	 */
	@Override
	public String toString() {
		return "MatchDTO [id=" + id + ", tournament=" + tournament + ", teamA=" + teamA + ", teamB=" + teamB + ", map="
				+ map + ", score=" + score + "]";
	}
}