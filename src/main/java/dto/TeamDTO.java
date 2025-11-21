package dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO for the Team entity.
 */
@Getter
@Setter
public class TeamDTO {
	private int id;
	private String name;
	private String region;
	
	/**
	 * Constructor of the Team DTO object.
	 * @param id id of the team
	 * @param name name of the team
	 * @param region region of the team
	 */
	protected TeamDTO(int id, String name, String region) {
		this.id = id;
		this.name = name;
		this.region = region;
	}

	/**
	 * Basic toString to display object properties.
	 */
	@Override
	public String toString() {
		return "TeamDTO [id=" + id + ", name=" + name + ", region=" + region + "]";
	}
}