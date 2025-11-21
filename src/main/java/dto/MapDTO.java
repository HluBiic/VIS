package dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO for the Map entity.
 */
@Getter
@Setter
public class MapDTO {
	private int id;
	private String name;
	
	/**
	 * Constructor of the Map DTO object.
	 * @param id id of the map
	 * @param name name of the map
	 */
	protected MapDTO(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	/**
	 * Basic toString to display object properties.
	 */
	@Override
	public String toString() {
		return "MapDTO [id=" + id + ", name=" + name + "]";
	}
}