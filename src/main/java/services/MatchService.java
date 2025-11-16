package services;

import java.util.ArrayList;
import java.util.List;

import app.utilities.Listener;
import dal.EntityKind;
import dal.repo.MapRepository;
import dal.repo.MatchRepository;
import dto.MapDTO;
import dto.MatchDTO;

public class MatchService {

	private final MatchRepository matchRepo = new MatchRepository();
	private final MapRepository mapRepo = new MapRepository();
	
	private final List<Listener> listeners = new ArrayList<>();
	
	public void addListener(Listener l) {
		listeners.add(l);
	}
	
	public MatchDTO newMatch(int mapId, String score, String caster) {
		MatchDTO match = matchRepo.insert(mapId, score);
		
		for(Listener l : listeners) {
			l.onCreated(match.getId(), EntityKind.MATCH, caster);
		}
		return match;
	}
	
	public List<MatchDTO> getAllMatches() {
		return matchRepo.findAll();
	}
	
	public String getMatchInfo(int id) {
		MatchDTO match = matchRepo.findById(id);
		MapDTO map = mapRepo.findById(match.getMap());
		//[ " + match.getId() + " ] - map: " + match.getMap());
		return "[ " + match.getId() + " ] - map: " + map.getName();
	}
}
