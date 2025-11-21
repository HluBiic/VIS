package dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO for the Tournament entity.
 */
@Getter
@Setter
public class TournamentDTO {
	private int id;
	private String name;
	private String location;
	private String date;
	
	/**
	 * Constructor of the Tournament DTO object.
	 * @param id id of the tournament
	 * @param name name of the tournament
	 * @param location location where the tournament was played
	 * @param date date when the tournament was played
	 */
	protected TournamentDTO(int id, String name, String location, String date) {
		this.id = id;
		this.name = name;
		this.location = location;
		this.date = date;
	}

	/**
	 * Basic toString to display object properties.
	 */
	@Override
	public String toString() {
		return "TournamentDTO [id=" + id + ", name=" + name + ", location=" + location + ", date=" + date + "]";
	}
}