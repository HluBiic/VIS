package dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TournamentDTO {
	private int id;
	private String name;
	private String location;
	private String date;
	
	protected TournamentDTO(int id, String name, String location, String date) {
		this.id = id;
		this.name = name;
		this.location = location;
		this.date = date;
	}

	@Override
	public String toString() {
		return "TournamentDTO [id=" + id + ", name=" + name + ", location=" + location + ", date=" + date + "]";
	}
}