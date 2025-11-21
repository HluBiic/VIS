package dal.repo;

import java.util.ArrayList;
import java.util.List;

import dal.gateway.TeamGateway;
import dto.TeamDTO;

/**
 * Class which provides higher layer of abstraction on top of gateway classes.
 */
public class TeamRepository {

	private TeamGateway teamGateway = new TeamGateway();
	
	/**
	 * Method which inserts hard coded teams into database if they dont exist.
	 */
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
	
	/**
	 * Method which inserts team into database through gateway.
	 * @param name name of the team
	 * @param region region of the team
	 * @return inserted team DTO object
	 */
	public TeamDTO insert(String name, String region) {
		return teamGateway.insert(name, region);
	}
	
	/**
	 * Method which updates team in database through gateway.
	 * @param t team with new data
	 * @return updated team DTO object
	 */
	public TeamDTO update(TeamDTO t) {
		return teamGateway.update(t);
	}
	
	/**
	 * Method which deletes team from database through gateway.
	 * @param id id of the team to delete
	 * @return deleted team DTO object
	 */
	public TeamDTO delete(int id) {
		return teamGateway.delete(id);
	}
	
	/**
	 * Method which finds team by id through gateway.
	 * @param id id of the team to look for
	 * @return team with given id if it exists in database
	 */
	public TeamDTO findById(int id) {
		return teamGateway.findById(id);
	}
	
	/**
	 * Method which finds all teams through gateway.
	 * @return list of all teams existing in database
	 */
	public List<TeamDTO> findAll() {
		return teamGateway.findAll();
	}
	
	/**
	 * Method which finds teams by given list of ids through gateway
	 * @param ids list of teams ids to look for
	 * @return teams with given ids if they exist in database
	 */
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
	
	/**
	 * Method which finds team by given name through gateway
	 * @param name name of the team to look for
	 * @return team with the given name if it exists in database
	 */
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