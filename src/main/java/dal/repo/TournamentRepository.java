package dal.repo;

import java.util.ArrayList;
import java.util.List;

import dal.gateway.TournamentGateway;
import dto.TournamentDTO;

/**
 * Class which provides higher layer of abstraction on top of gateway classes.
 */
public class TournamentRepository {

	private TournamentGateway tourGateway = new TournamentGateway();
	
	/**
	 * Method which inserts hard coded tournaments into database if they dont exist.
	 */
	public void init() {
		if (findByName("Six Invitational 2024") == null) {
			tourGateway.insert("Six Invitational 2024", "Brazil", "13.02.2024");
		}
		if (findByName("November Major 2023") == null) {
			tourGateway.insert("November Major 2023", "Sweden", "20.11.2023");
		}
		if (findByName("Esports World Cup 2025") == null) {
			tourGateway.insert("Esports World Cup 2025", "Saudi Arabia", "05.08.2025");
		}
		if (findByName("BLAST R6 Major 2025") == null) {
			tourGateway.insert("BLAST R6 Major 2025", "Germany", "08.11.2025");
		}
	}
	
	/**
	 * Method which inserts tournament into database through gateway.
	 * @param name name of the tournament
	 * @param location location of the tournament
	 * @param date date of the tournament
	 * @return inserted torunament DTO object
	 */
	public TournamentDTO insert(String name, String location, String date) {
		return tourGateway.insert(name, location, date);
	}
	
	/**
	 * Method which updates tournament in database through gateway.
	 * @param t tournament with new data
	 * @return updated tournament DTO object
	 */
	public TournamentDTO update(TournamentDTO t) {
		return tourGateway.update(t);
	}
	
	/**
	 * Method which deletes tournament from database through gateway.
	 * @param id id of the tournament to delete
	 * @return deleted tournament DTO object
	 */
	public TournamentDTO delete(int id) {
		return tourGateway.delete(id);
	}
	
	/**
	 * Method which finds tournament by id through gateway.
	 * @param id id of the tournament to look for
	 * @return tournament with given id if it exists in database
	 */
	public TournamentDTO findById(int id) {
		return tourGateway.findById(id);
	}
	
	/**
	 * Method which finds all tournaments through gateway.
	 * @return list of all tournaments existing in database
	 */
	public List<TournamentDTO> findAll() {
		return tourGateway.findAll();
	}
	
	/**
	 * Method which finds tournaments by given list of ids through gateway
	 * @param ids list of tournaments ids to look for
	 * @return tournaments with given ids if they exist in database
	 */
	public List<TournamentDTO> findByIds(List<Integer> ids) {
		List<TournamentDTO> results = new ArrayList<TournamentDTO>();
		for (Integer id : ids) {
			TournamentDTO t = tourGateway.findById(id);
			if (t != null) {
				results.add(t);
			}
		}
		return results;
	}
	
	/**
	 * Method which finds tournament by given name through gateway
	 * @param name name of the tournament to look for
	 * @return map with the given tournament if it exists in database
	 */
	public TournamentDTO findByName(String name) {
		List <TournamentDTO> tournaments = tourGateway.findAll();
		for (TournamentDTO t : tournaments) {
			if (t.getName().equals(name)) {
				return t;
			}
		}
		return null;
	}
}