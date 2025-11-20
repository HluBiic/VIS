package dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamDTO {
	private int id;
	private String name;
	private String region;
	
	protected TeamDTO(int id, String name, String region) {
		this.id = id;
		this.name = name;
		this.region = region;
	}

	@Override
	public String toString() {
		return "TeamDTO [id=" + id + ", name=" + name + ", region=" + region + "]";
	}
}
