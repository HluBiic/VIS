package services;

import java.util.ArrayList;
import java.util.List;

import app.utilities.Listener;
import dal.EntityKind;
import dal.UnitOfWork;
import dal.repo.MapRepository;
import dal.repo.MatchRepository;
import dto.AllDTOFactory;
import dto.MapDTO;
import dto.MatchDTO;
import mapper.Mapper;
import model.Map;
import model.Match;

public class MatchService {

	private final MatchRepository matchRepo = new MatchRepository();
	private final MapRepository mapRepo = new MapRepository();
	
	private final List<Listener> listeners = new ArrayList<>();
	
	public void addListener(Listener l) {
		listeners.add(l);
	}
	
	public String getMatchInfo(int id) {
		Match match = Mapper.toDomain(matchRepo.findById(id));
		Map map = Mapper.toDomain(mapRepo.findById(match.getMap().getId()));
		//[ " + match.getId() + " ] - map: " + match.getMap());
		return "[ " + match.getId() + " ] - map: " + map.getName();
	}
	
	
	/*public Match newMatch(int mapId, String score, String caster) {
		Match match = Mapper.toDomain(matchRepo.insert(mapId, score));
		for (Listener l : listeners) {
			l.onCreated(match.getMap().getId(), EntityKind.MATCH, caster);
		}
		return match;
	}*/
	public Match newMatch(int mapId, String score, String caster) {
		UnitOfWork uow = new UnitOfWork();
		try {
			uow.begin();
			MatchDTO dto = AllDTOFactory.createMatch(0, mapId, score);
			uow.registerNew(dto);
			uow.commit();
			Match m = Mapper.toDomain(dto);
			
			for (Listener l : listeners) {
				l.onCreated(m.getId(), EntityKind.MATCH, caster);
			}
			//uow.end();
			return m;
		} catch (Exception e) {
			throw new RuntimeException("Match creation failed: " + e.getMessage());
		}
	}
	
	public List<Match> getAllMatches() {
		List<Match> result = new ArrayList<>();
		List<MatchDTO> matches = matchRepo.findAll();
		for (MatchDTO m : matches) {
			result.add(Mapper.toDomain(m));
		}
		return result;
	}
	
}
