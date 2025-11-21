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

/**
 * Mapper class for transfroming DTO objects to Domain and vice versa.
 */
public class Mapper {
	private static final MapRepository mapRepo = new MapRepository();
	private static final TournamentRepository tourRepo = new TournamentRepository();
	private static final TeamRepository teamRepo = new TeamRepository();

	/**
	 * Method for transforming a Match domain object to Match DTO object.
	 * @param m Match domain object to be transformed
	 * @return transformed Match DTO object
	 */
	public static MatchDTO toDto(Match m) {
		return AllDTOFactory.createMatch(m.getId(), m.getTournament().getId(), m.getTeamA().getId(), m.getTeamB().getId() ,m.getMap().getId(), m.getScore());
	}
	
	/**
	 * Method for transforming a Match DTO object to Match domain object.
	 * @param m Match DTO object to be transformed
	 * @return transformed Match domain object
	 */
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
	
	
	/**
	 * Method for transforming a Map domain object to Map DTO object.
	 * @param m Map domain object to be transformed
	 * @return transformed Map DTO object
	 */
	public static MapDTO toDto(Map m) {
		return AllDTOFactory.createMap(m.getId(), m.getName());
	}
	
	/**
	 * Method for transforming a Map DTO object to Map domain object.
	 * @param m Map DTO object to be transformed
	 * @return transformed Map domain object
	 */
	public static Map toDomain(MapDTO m) {
		return AllModelFactory.createMap(m.getId(), m.getName());
	}
	
	
	/**
	 * Method for transforming a Tournament domain object to Tournament DTO object.
	 * @param t Tournament domain object to be transformed
	 * @return transformed Tournament DTO object
	 */
	public static TournamentDTO toDto(Tournament t) {
		return AllDTOFactory.createTournament(t.getId(), t.getName(), t.getLocation(), t.getDate());
	}
	
	/**
	 * Method for transforming a Tournament DTO object to Tournament domain object.
	 * @param t Tournament DTO object to be transformed
	 * @return transformed Tournament domain object
	 */
	public static Tournament toDomain(TournamentDTO t) {
		return AllModelFactory.createTournament(t.getId(), t.getName(), t.getLocation(), t.getDate());
	}
	
	
	/**
	 * Method for transforming a Team domain object to Team DTO object.
	 * @param t Team domain object to be transformed
	 * @return transformed Team DTO object
	 */
	public static TeamDTO toDto(Team t) {
		return AllDTOFactory.createTeam(t.getId(), t.getName(), t.getRegion());
	}
	
	/**
	 * Method for transforming a Team DTO object to Team domain object.
	 * @param t Team DTO object to be transformed
	 * @return transformed Team domain object
	 */
	public static Team toDomain(TeamDTO t) {
		return AllModelFactory.createTeam(t.getId(), t.getName(), t.getRegion());
	}
}