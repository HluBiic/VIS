package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MapDTO {
	private int id;
	private String name;
	
	public MapDTO(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "MapDTO [id=" + id + ", name=" + name + "]";
	}
}
