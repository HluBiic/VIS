package services;

import java.util.ArrayList;
import java.util.List;

import dal.UnitOfWork;
import dal.repo.TournamentRepository;
import dto.AllDTOFactory;
import dto.TournamentDTO;
import mapper.Mapper;
import model.Tournament;

/**
 * Provides service layer operations for managing Tournament entities, handling business logic
 * and interaction with the data access layer (TournamentRepository).
 */
public class TournamentService {
	
	private final TournamentRepository tourRepo = new TournamentRepository();
	
	/**
	 * Initializes the database with a default set of tournaments if they do not already exist.
	 */
	public void initTournaments() {
		tourRepo.init();
	}
	
	/**
	 * Creates a new Tournament entity, registers it with the Unit of Work, and commits the transaction.
	 */
	public Tournament newTournament(String name, String location, String date, String caster) {
		UnitOfWork uow = new UnitOfWork();
		try {
			uow.begin();
			TournamentDTO dto = AllDTOFactory.createTournament(0, name, location, date);
			uow.registerNew(dto);
			uow.commit();
			Tournament t = Mapper.toDomain(dto);
			return t;
		} catch (Exception e) {
			throw new RuntimeException("Tournament creation failed: " + e.getMessage());
		}
	}
	
	/**
	 * Retrieves all tournaments from the database and converts them into domain models.
	 */
	public List<Tournament> getAllTournaments() {
		List<Tournament> result = new ArrayList<>();
		List<TournamentDTO> tours = tourRepo.findAll();
		for (TournamentDTO t : tours) {
			result.add(Mapper.toDomain(t));
		}
		return result;
	}
	
	/**
	 * Retrieves a single Tournament entity based on its name and converts it to a domain model.
	 */
	public Tournament getTourByName(String name) {
		return Mapper.toDomain(tourRepo.findByName(name));
	}
	
	/**
	 * Retrieves a single Tournament entity based on its ID and converts it to a domain model.
	 */
	public Tournament getTourById(int id) {
		return Mapper.toDomain(tourRepo.findById(id));
	}
}