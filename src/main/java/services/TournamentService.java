package services;

import java.util.ArrayList;
import java.util.List;

import app.utilities.Listener;
import dal.EntityKind;
import dal.UnitOfWork;
import dal.repo.TournamentRepository;
import dto.AllDTOFactory;
import dto.TournamentDTO;
import mapper.Mapper;
import model.Tournament;

public class TournamentService {
	
	private final TournamentRepository tourRepo = new TournamentRepository();
	
	public void initTournaments() {
		tourRepo.init();
	}
	
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
	
	public List<Tournament> getAllTournaments() {
		List<Tournament> result = new ArrayList<>();
		List<TournamentDTO> tours = tourRepo.findAll();
		for (TournamentDTO t : tours) {
			result.add(Mapper.toDomain(t));
		}
		return result;
	}
	
	public Tournament getTourByName(String name) {
		return Mapper.toDomain(tourRepo.findByName(name));
	}
	
	public Tournament getTourById(int id) {
		return Mapper.toDomain(tourRepo.findById(id));
	}

}
