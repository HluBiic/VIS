package model;

import lombok.Getter;
import lombok.Setter;

/**
 * Domain model of the Match entity.
 */
@Getter
@Setter
public class Match {
    private int id;
    private Tournament tournament;
    private Team teamA;
    private Team teamB;
    private Map map;
    private String score;
    
    /**
     * Constructor of the Match object.
     * @param id id of the match
     * @param tournament the tournament the match is played on
     * @param teamA the first team
     * @param teamB the second team
     * @param map id the map the match is played on
     * @param score result of the match in format [teamA points-teamB points] 
     */
	protected Match(int id, Tournament tournament, Team teamA, Team teamB, Map map, String score) {
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
		return "Match [id=" + id + ", tournament=" + tournament + ", teamA=" + teamA + ", teamB=" + teamB + ", map="
				+ map + ", score=" + score + "]";
	}
}