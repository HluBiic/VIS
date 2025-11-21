package model;

import lombok.Getter;
import lombok.Setter;

/**
 * Domain model of the Map entity.
 */
@Getter
@Setter
public class Map {
	private int id;
	private String name;
	
	/**
	 * Constructor of the Map object. 
	 * @param id id of the map
	 * @param name name of the map
	 */
	protected Map(int id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * Basic toString to display object properties.
	 */
	@Override
	public String toString() {
		return "Map [id=" + id + ", name=" + name + "]";
	}
}