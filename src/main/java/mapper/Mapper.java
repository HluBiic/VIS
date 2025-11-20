package mapper;

import dal.repo.MapRepository;
import dal.repo.TeamRepository;
import dal.repo.TournamentRepository;
import dto.AllDTOFactory;
import dto.MapDTO;
import dto.MatchDTO;
import dto.TeamDTO;
import dto.TournamentDTO;
import model.AllModelFactory;
import model.Match;
import model.Team;
import model.Tournament;
import model.Map;

public class Mapper {

	private static final MapRepository mapRepo = new MapRepository();
	private static final TournamentRepository tourRepo = new TournamentRepository();
	private static final TeamRepository teamRepo = new TeamRepository();

	public static MatchDTO toDto(Match m) {
		return AllDTOFactory.createMatch(m.getId(), m.getTournament().getId(), m.getTeamA().getId(), m.getTeamB().getId() ,m.getMap().getId(), m.getScore());
	}
	
	public static Match toDomain(MatchDTO m) {
		MapDTO mapDTO = mapRepo.findById(m.getMap());
		Map map = AllModelFactory.createMap(mapDTO.getId(), mapDTO.getName());
		
		TournamentDTO tourDTO = tourRepo.findById(m.getTournament());
		Tournament tour = AllModelFactory.createTournament(tourDTO.getId(), tourDTO.getName(), tourDTO.getLocation(), tourDTO.getDate());
		
		TeamDTO teamADTO = teamRepo.findById(m.getTeamA());
		TeamDTO teamBDTO = teamRepo.findById(m.getTeamB());
		Team teamA = AllModelFactory.createTeam(teamADTO.getId(), teamADTO.getName(), teamADTO.getRegion());
		Team teamB = AllModelFactory.createTeam(teamBDTO.getId(), teamBDTO.getName(), teamBDTO.getRegion());
		return AllModelFactory.createMatch(m.getId(), tour, teamA, teamB, map, m.getScore());
	}
	
	
	public static MapDTO toDto(Map m) {
		return AllDTOFactory.createMap(m.getId(), m.getName());
	}
	
	public static Map toDomain(MapDTO m) {
		return AllModelFactory.createMap(m.getId(), m.getName());
	}
	
	
	public static TournamentDTO toDto(Tournament t) {
		return AllDTOFactory.createTournament(t.getId(), t.getName(), t.getLocation(), t.getDate());
	}
	
	public static Tournament toDomain(TournamentDTO t) {
		return AllModelFactory.createTournament(t.getId(), t.getName(), t.getLocation(), t.getDate());
	}
	
	
	public static TeamDTO toDto(Team t) {
		return AllDTOFactory.createTeam(t.getId(), t.getName(), t.getRegion());
	}
	
	public static Team toDomain(TeamDTO t) {
		return AllModelFactory.createTeam(t.getId(), t.getName(), t.getRegion());
	}
}

