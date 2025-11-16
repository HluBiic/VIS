package services;

import java.util.ArrayList;
import java.util.List;

import app.utilities.Listener;
import dal.EntityKind;
import dal.repo.MapRepository;
import dto.MapDTO;

public class MapService {

	private final MapRepository mapRepo = new MapRepository();
	private final List<Listener> listeners = new ArrayList();
	
	public void addListener(Listener l) {
		listeners.add(l);
	}
	
	public MapDTO newMap(String name, String caster) {
		MapDTO map = mapRepo.insert(name);
		
        for (Listener l : listeners) {
            l.onCreated(map.getId(), EntityKind.MAP, caster);
        }
		return map;
	}
	
	public List<MapDTO> getAllMaps() {
		return mapRepo.findAll();
	}
}
