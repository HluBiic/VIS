package dal.repo;

import java.util.ArrayList;
import java.util.List;

import dal.gateway.TournamentGateway;
import dto.TournamentDTO;

public class TournamentRepository {

	private TournamentGateway tourGateway = new TournamentGateway();
	
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
	
	public TournamentDTO insert(String name, String location, String date) {
		return tourGateway.insert(name, location, date);
	}
	
	public TournamentDTO update(TournamentDTO t) {
		return tourGateway.update(t);
	}
	
	public TournamentDTO delete(int id) {
		return tourGateway.delete(id);
	}
	
	public TournamentDTO findById(int id) {
		return tourGateway.findById(id);
	}
	
	public List<TournamentDTO> findAll() {
		return tourGateway.findAll();
	}
	
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
