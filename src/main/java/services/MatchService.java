package services;

import java.util.ArrayList;
import java.util.HashMap;
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
import dto.TeamDTO;
import mapper.Mapper;
import model.Map;
import model.Match;
import model.Team;
import model.Tournament;

/**
 * Provides service operations for managing Match entities, including CRUD operations, 
 * data retrieval, logging, and brief statistical calculations.
 */
public class MatchService {

	private final MatchRepository matchRepo = new MatchRepository();
	private final MapRepository mapRepo = new MapRepository();
	private final TournamentRepository tourRepo = new TournamentRepository();
	private final TeamRepository teamRepo = new TeamRepository();
	
	private final List<Listener> listeners = new ArrayList<>();
	
	/**
	 * Registers a logger to receive notifications about CRUD events.
	 */
	public void addListener(Listener l) {
		listeners.add(l);
	}
	
	/**
	 * Retrieves and formats a comprehensive information string for a specific match ID.
	 */
	public String getMatchInfo(int id) {
		Match match = Mapper.toDomain(matchRepo.findById(id));
		Map map = Mapper.toDomain(mapRepo.findById(match.getMap().getId()));
		Tournament t = Mapper.toDomain(tourRepo.findById(match.getTournament().getId()));
		Team teamA = Mapper.toDomain(teamRepo.findById(match.getTeamA().getId()));
		Team teamB = Mapper.toDomain(teamRepo.findById(match.getTeamB().getId()));
		return "[ " + match.getId() + " ] Tour.: " + t.getName() + " Teams: " + teamA.getName() + " vs " + teamB.getName() + ", Map: " + map.getName();
	}
	
	/**
	 * Creates a new match, commits it via UnitOfWork, and notifies listeners of the creation.
	 */
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
	
	/**
	 * Updates an existing match, commits the change via UnitOfWork, and notifies listeners of the update.
	 */
	public Match updateMatch(int matchId, int tourId, int teamAid, int teamBid, int mapId, String score, String caster) {
		UnitOfWork uow = new UnitOfWork();
		try {
			uow.begin();
			MatchDTO dto = AllDTOFactory.createMatch(matchId, tourId, teamAid, teamBid, mapId, score);
			uow.registerModified(dto);
			uow.commit();
			Match m = Mapper.toDomain(dto);
			
			for (Listener l : listeners) {
				l.onUpdated(m.getId(), EntityKind.MATCH, caster);
			}
			return m;
		} catch (Exception e) {
			throw new RuntimeException("Match update failed: " + e.getMessage());
		}
	}
	
	/**
	 * Deletes a match, commits the removal via UnitOfWork, and notifies listeners of the deletion.
	 */
	public Match deleteMatch(Match m, String caster) {
		UnitOfWork uow = new UnitOfWork();
		try {
			uow.begin();
			uow.registerRemoved(Mapper.toDto(m));
			uow.commit();
			
			for (Listener l : listeners) {
				l.onDeleted(m.getId(), EntityKind.MATCH, caster);
			}
			return m;
		} catch (Exception e) {
			throw new RuntimeException("Match deletion failed: " + e.getMessage());
		}
	}
	
	/**
	 * Retrieves all match entities from the repository and converts them to domain models.
	 */
	public List<Match> getAllMatches() {
		List<Match> result = new ArrayList<>();
		List<MatchDTO> matches = matchRepo.findAll();
		for (MatchDTO m : matches) {
			result.add(Mapper.toDomain(m));
		}
		return result;
	}
	
	/**
	 * Retrieves a single match by its ID and converts it to a domain model.
	 */
	public Match getMatchById(int id) {
		return Mapper.toDomain(matchRepo.findById(id));
	}
	
	/**
	 * Retrieves all matches associated with a specific tournament ID.
	 */
	public List<Match> getAllTourMatches(int id) {
		List<Match> result = new ArrayList<>();
		List<MatchDTO> matches = matchRepo.findAll();
		
		for (MatchDTO m: matches) {
			if (m.getTournament() == id) {
				result.add(Mapper.toDomain(m));
			}
		}
		return result;
	}
	
	/**
	 * Calculates and returns the map that has been played the most times in a given tournament.
	 */
	public Map getMostPlayedMap(int tournamentId) {
	    List<MatchDTO> matches = matchRepo.findAll();
	    HashMap<Integer, Integer> countMap = new HashMap<>();
	    for (MatchDTO m : matches) {
	        if (m.getTournament() == tournamentId) {
	            int mapID = m.getMap();
	            countMap.put(mapID, countMap.getOrDefault(mapID, 0) + 1);
	        }
	    }
	    if (countMap.isEmpty()) {
	        return null;
	    }

	    int mostPlayedMapID = -1;
	    int maxCount = 0;
	    for (var entry : countMap.entrySet()) {
	        if (entry.getValue() > maxCount) {
	            mostPlayedMapID = entry.getKey();
	            maxCount = entry.getValue();
	        }
	    }
	    MapDTO mapDto = mapRepo.findById(mostPlayedMapID);
	    if (mapDto == null) {
	        return null;
	    }
	    return Mapper.toDomain(mapDto);
	}
	
	/**
	 * Determines and returns the team with the highest number of wins in the specified tournament.
	 */
	public Team getBestTeam(int tournamentId) {
	    List<MatchDTO> matches = matchRepo.findAll();
	    HashMap<Integer, Integer> winCount = new HashMap<>();

	    for (MatchDTO m : matches) {
	        if (m.getTournament() != tournamentId) continue;

	        String score = m.getScore();
	        if (score == null || !score.contains("-")) continue;

	        try {
	            String[] split = score.split("-");
	            
	            if (split.length < 2) continue; 

	            int aScore = Integer.parseInt(split[0].trim());
	            int bScore = Integer.parseInt(split[1].trim());

	            int winnerId = -1;
	            if (aScore > bScore) {
	                winnerId = m.getTeamA();
	            } else if (bScore > aScore) {
	                winnerId = m.getTeamB();
	            } else {
	                continue;
	            }

	            if (teamRepo.findById(winnerId) == null) continue;
	            winCount.put(winnerId, winCount.getOrDefault(winnerId, 0) + 1);

	        } catch (NumberFormatException e) {
	            System.out.println("Skipping invalid score: " + score);
	            continue; 
	        }
	    }

	    if (winCount.isEmpty()) return null;

	    int bestTeamId = -1;
	    int maxWins = -1;
	    for (var entry : winCount.entrySet()) {
	        if (entry.getValue() > maxWins) {
	            bestTeamId = entry.getKey();
	            maxWins = entry.getValue();
	        }
	    }
	    TeamDTO dto = teamRepo.findById(bestTeamId);
	    if (dto == null) return null;
	    return Mapper.toDomain(dto);
	}
}