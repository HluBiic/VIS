package model;

import lombok.Getter;
import lombok.Setter;

/**
 * Domain model of the Tournament entity.
 */
@Getter
@Setter
public class Tournament {
	private int id;
	private String name;
	private String location;
	private String date;
	
	/**
	 * Constructor of the Tournament object.
	 * @param id id of the tournament
	 * @param name name of the tournament
	 * @param location location where the tournament was played
	 * @param date date when the tournament was played
	 */
	public Tournament(int id, String name, String location, String date) {
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
		return "Tournament [id=" + id + ", name=" + name + ", location=" + location + ", date=" + date + "]";
	}
}