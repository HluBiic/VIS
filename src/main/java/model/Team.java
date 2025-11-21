package model;

import lombok.Getter;
import lombok.Setter;

/**
 * Domain model of the Team entity.
 */
@Getter
@Setter
public class Team {
	private int id;
	private String name;
	private String region;
	
	/**
	 * Constructor of the Team object.
	 * @param id is of the team
	 * @param name name of the team
	 * @param region region of the team
	 */
	protected Team(int id, String name, String region) {
		this.id = id;
		this.name = name;
		this.region = region;
	}
	
	/**
	 * Basic toString to display object properties.
	 */
	@Override
	public String toString() {
		return "Team [id=" + id + ", name=" + name + ", region=" + region + "]";
	}
}