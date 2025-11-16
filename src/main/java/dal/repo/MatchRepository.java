package dal.repo;

import java.util.List;

import dal.gateway.MatchGateway;
import dto.MatchDTO;

public class MatchRepository {

	private MatchGateway matchGateway = new MatchGateway();
	
	public MatchDTO insert(int mapId, String score) {
		return matchGateway.insert(mapId, score);
	}
	
	public MatchDTO update(MatchDTO match) {
		//TODO
		return null;
	}
	
	public MatchDTO delete(int id) {
		//TODO
		return null;
	}
	
	public MatchDTO findById(int id) {
		//TODO
		return matchGateway.findById(id);
	}
	
	public List<MatchDTO> findAll() {
		return matchGateway.findAll();
	}
	
	public List<MatchDTO> findByIds(List<Integer> ids) {
		//TODO
		return null;
	}
}
