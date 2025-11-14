package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Map {
	private int id;
	private String name;
	
	protected Map(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Map [id=" + id + ", name=" + name + "]";
	}
}
