package dal.repo;

import java.util.ArrayList;
import java.util.List;

import dal.gateway.MatchGateway;
import dto.MatchDTO;

public class MatchRepository {

	private MatchGateway matchGateway = new MatchGateway();
	
	public MatchDTO insert(int tourId, int teamAid, int teamBid, int mapId, String score) {
		return matchGateway.insert(tourId, teamAid, teamBid, mapId, score);
	}
	
	public MatchDTO update(MatchDTO match) {
		return matchGateway.update(match);
	}
	
	public MatchDTO delete(int id) {
		return matchGateway.delete(id);
	}
	
	public MatchDTO findById(int id) {
		return matchGateway.findById(id);
	}
	
	public List<MatchDTO> findAll() {
		return matchGateway.findAll();
	}
	
	public List<MatchDTO> findByIds(List<Integer> ids) {
		List<MatchDTO> results = new ArrayList<MatchDTO>();
		for (Integer id : ids) {
			MatchDTO m = matchGateway.findById(id);
			if (m != null) {
				results.add(m);
			}
		}
		return results;
	}
	
	//TODO - treba vylistovat vsetke zapasy daneho turnaja
	/*public List<MatchDTO> findMatchesByTournament(int tournament) {
		List<MatchDTO> matches = matchGateway.findAll();
		List<MatchDTO> result = new ArrayList<>();
		for(MatchDTO m : matches) {
			if (m.getTournament() == tournament) {
				result.add(m);
			}
		}
		return result;
	}*/
}
