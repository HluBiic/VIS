package services;

import java.util.ArrayList;
import java.util.List;

import dal.UnitOfWork;
import dal.repo.TeamRepository;
import dto.AllDTOFactory;
import dto.TeamDTO;
import mapper.Mapper;
import model.Team;

/**
 * Provides service layer operations for managing Team entities, handling business logic
 * and interaction with the data access layer (TeamRepository).
 */
public class TeamService {

	private final TeamRepository teamRepo = new TeamRepository();
	
	/**
	 * Initializes the database with a default set of teams if they do not already exist.
	 */
	public void initTeams() {
		teamRepo.init();
	}
	
	/**
	 * Creates a new Team entity, registers it with the Unit of Work, and commits the transaction.
	 */
	public Team newTeam(String name, String region, String caster) {
		UnitOfWork uow = new UnitOfWork();
		try {
			uow.begin();
			TeamDTO dto = AllDTOFactory.createTeam(0, name, region);
			uow.registerNew(dto);
			uow.commit();
			Team t = Mapper.toDomain(dto);
			return t;
		} catch (Exception e) {
			throw new RuntimeException("Team creation failed: " + e.getMessage());
		}
	}
	
	/**
	 * Retrieves all teams from the database and converts them into domain models.
	 */
	public List<Team> getAllTeams() {
		List<Team> result = new ArrayList<>();
		List<TeamDTO> teams = teamRepo.findAll();
		for (TeamDTO t : teams) {
			result.add(Mapper.toDomain(t));
		}
		return result;
	}
	
	/**
	 * Retrieves a single Team entity based on its name and converts it to a domain model.
	 */
	public Team getTeamByName(String name) {
		return Mapper.toDomain(teamRepo.findByName(name));
	}
}