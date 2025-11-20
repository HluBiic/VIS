package services;

import java.util.ArrayList;
import java.util.List;

import dal.UnitOfWork;
import dal.repo.TeamRepository;
import dto.AllDTOFactory;
import dto.TeamDTO;
import mapper.Mapper;
import model.Team;

public class TeamService {

	private final TeamRepository teamRepo = new TeamRepository();
	
	public void initTeams() {
		teamRepo.init();
	}
	
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
	
	public List<Team> getAllTeams() {
		List<Team> result = new ArrayList<>();
		List<TeamDTO> teams = teamRepo.findAll();
		for (TeamDTO t : teams) {
			result.add(Mapper.toDomain(t));
		}
		return result;
	}
	
	public Team getTeamByName(String name) {
		return Mapper.toDomain(teamRepo.findByName(name));
	}
}
