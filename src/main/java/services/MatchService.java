package services;

import java.util.ArrayList;
import java.util.List;

import app.utilities.Listener;
import dal.EntityKind;
import dal.UnitOfWork;
import dal.repo.MapRepository;
import dal.repo.MatchRepository;
import dal.repo.TeamRepository;
import dal.repo.TournamentRepository;
import dto.AllDTOFactory;
import dto.MapDTO;
import dto.MatchDTO;
import mapper.Mapper;
import model.Map;
import model.Match;
import model.Team;
import model.Tournament;

public class MatchService {

	private final MatchRepository matchRepo = new MatchRepository();
	private final MapRepository mapRepo = new MapRepository();
	private final TournamentRepository tourRepo = new TournamentRepository();
	private final TeamRepository teamRepo = new TeamRepository();
	
	private final List<Listener> listeners = new ArrayList<>();
	
	public void addListener(Listener l) {
		listeners.add(l);
	}
	
	public String getMatchInfo(int id) {
		Match match = Mapper.toDomain(matchRepo.findById(id));
		Map map = Mapper.toDomain(mapRepo.findById(match.getMap().getId()));
		Tournament t = Mapper.toDomain(tourRepo.findById(match.getTournament().getId()));
		Team teamA = Mapper.toDomain(teamRepo.findById(match.getTeamA().getId()));
		Team teamB = Mapper.toDomain(teamRepo.findById(match.getTeamB().getId()));
		return "[ " + match.getId() + " ] Tour.: " + t.getName() + " Teams: " + teamA.getName() + " vs " + teamB.getName() + ", Map: " + map.getName();
	}
	
	public Match newMatch(int tourId, int teamAid, int teamBid, int mapId, String score, String caster) {
		UnitOfWork uow = new UnitOfWork();
		try {
			uow.begin();
			MatchDTO dto = AllDTOFactory.createMatch(0, tourId, teamAid, teamBid, mapId, score);
			uow.registerNew(dto);
			uow.commit();
			Match m = Mapper.toDomain(dto);
			
			for (Listener l : listeners) {
				l.onCreated(m.getId(), EntityKind.MATCH, caster);
			}
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
