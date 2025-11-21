package dal.repo;

import java.util.ArrayList;
import java.util.List;

import dal.gateway.MatchGateway;
import dto.MatchDTO;

/**
 * Class which provides higher layer of abstraction on top of gateway classes.
 */
public class MatchRepository {

	private MatchGateway matchGateway = new MatchGateway();
	
	/**
	 * Method which inserts match into database through gateway.
	 * @param tourId id of the tournament
	 * @param teamAid id of the first team
	 * @param teamBid id of the second team
	 * @param mapId id of the map
	 * @param score result of the match
	 * @return inserted match DTO object
	 */
	public MatchDTO insert(int tourId, int teamAid, int teamBid, int mapId, String score) {
		return matchGateway.insert(tourId, teamAid, teamBid, mapId, score);
	}
	
	/**
	 * Method which updates match in database through gateway.
	 * @param match match with new data
	 * @return updated match DTO object
	 */
	public MatchDTO update(MatchDTO match) {
		return matchGateway.update(match);
	}
	
	/**
	 * Method which deletes match from database through gateway.
	 * @param id id of the match to delete
	 * @return deleted match DTO object
	 */
	public MatchDTO delete(int id) {
		return matchGateway.delete(id);
	}
	
	/**
	 * Method which finds match by id through gateway.
	 * @param id id of the match to look for
	 * @return match with given id if it exists in database
	 */
	public MatchDTO findById(int id) {
		return matchGateway.findById(id);
	}
	
	/**
	 * Method which finds all matches through gateway.
	 * @return list of all matches existing in database
	 */
	public List<MatchDTO> findAll() {
		return matchGateway.findAll();
	}
	
	/**
	 * Method which finds matches by given list of ids through gateway
	 * @param ids list of matches ids to look for
	 * @return matches with given ids if they exist in database
	 */
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
}