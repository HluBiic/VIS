package dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchDTO {
	private int id;
	private int tournament;
	private int teamA;
	private int teamB;
	private int map; //foreign key only...in domain model class we have object directly
	private String score;
	
	protected MatchDTO(int id, int tournament, int teamA, int teamB, int map, String score) {
		this.id = id;
		this.tournament = tournament;
		this.teamA = teamA;
		this.teamB = teamB;
		this.map = map;
		this.score = score;
	}

	@Override
	public String toString() {
		return "MatchDTO [id=" + id + ", tournament=" + tournament + ", teamA=" + teamA + ", teamB=" + teamB + ", map="
				+ map + ", score=" + score + "]";
	}
}
