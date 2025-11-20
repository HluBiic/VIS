package dal.repo;

import java.util.ArrayList;
import java.util.List;

import dal.gateway.TeamGateway;
import dto.TeamDTO;

public class TeamRepository {

	private TeamGateway teamGateway = new TeamGateway();
	
	public void init() {
		if (findByName("FAZE") == null) {
			teamGateway.insert("FAZE", "LATAM");
		}
		if (findByName("G2") == null) {
			teamGateway.insert("G2", "EUL");
		}
		if (findByName("W7M") == null) {
			teamGateway.insert("W7M", "LATAM");
		}
		if (findByName("DZ") == null) {
			teamGateway.insert("DZ", "NAL");
		}
		if (findByName("TL") == null) {
			teamGateway.insert("TL", "LATAM");
		}
		if (findByName("BDS") == null) {
			teamGateway.insert("BDS", "EUL");
		}
	}
	
	public TeamDTO insert(String name, String region) {
		return teamGateway.insert(name, region);
	}
	
	public TeamDTO update(TeamDTO t) {
		return teamGateway.update(t);
	}
	
	public TeamDTO delete(int id) {
		return teamGateway.delete(id);
	}
	
	public TeamDTO findById(int id) {
		return teamGateway.findById(id);
	}
	
	public List<TeamDTO> findAll() {
		return teamGateway.findAll();
	}
	
	public List<TeamDTO> findByIds(List<Integer> ids) {
		List<TeamDTO> results = new ArrayList<TeamDTO>();
		for (Integer id : ids) {
			TeamDTO t = teamGateway.findById(id);
			if (t != null) {
				results.add(t);
			}
		}
		return results;
	}
	
	public TeamDTO findByName(String name) {
		List<TeamDTO> teams = teamGateway.findAll();
		for (TeamDTO t : teams) {
			if (t.getName().equals(name)) {
				return t;
			}
		}
		return null;
	}
}
