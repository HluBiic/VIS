package dal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dal.repo.MapRepository;
import dal.repo.MatchRepository;
import dto.MapDTO;
import dto.MatchDTO;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class UnitOfWork {
	private DBConnection con;

	private Map<EntityKind, List<Object>> newEntities = new HashMap<>();
	private Map<EntityKind, List<Object>> modifiedEntities = new HashMap<>();
	private Map<EntityKind, List<Object>> removedEntities = new HashMap<>();
	
	private final MapRepository mapRepo = new MapRepository();
	private final MatchRepository matchRepo = new MatchRepository();
	
	public void begin() throws SQLException {
		this.con = DBConnection.getInstance();
		try {
			this.con.getCon().setAutoCommit(false);
			log.info("Transaction started");
		} catch (SQLException e) {
			throw new RuntimeException("Failed to begin transaction: " + e.getMessage());
		}
	}
	
	public void commit() {
		try {
			if (this.con.getCon() == null) {
				begin();
			}
			
			insertNewEntities();
			updateModifiedEntities();
			deleteRemovedEntities();
			
			this.con.getCon().commit();
			
		} catch (Exception e) {
            log.error("UnitOfWork: Commit failed, performing rollback. Reason: " + e.getMessage());
            rollback();
		}
	}
	
	//----------------------------------------------------------------------
	public void registerNew(Object o) {
		if (o instanceof MapDTO) {
			this.newEntities.computeIfAbsent(EntityKind.MAP, k -> new ArrayList<>()).add(o);
		} else if (o instanceof MatchDTO) {
			this.newEntities.computeIfAbsent(EntityKind.MATCH, k -> new ArrayList<>()).add(o);
		} //TODO ostatne entityKind
	}
	
	public void registerModified(Object o) {
		if (o instanceof MapDTO) {
			this.modifiedEntities.computeIfAbsent(EntityKind.MAP, k -> new ArrayList<>()).add(o);
		} else if (o instanceof MatchDTO) {
			this.modifiedEntities.computeIfAbsent(EntityKind.MATCH, k -> new ArrayList<>()).add(o);
		} //TODO ostatne entityKind
	}
	
	public void registerRemoved(Object o) {
		if (o instanceof MapDTO) {
			this.removedEntities.computeIfAbsent(EntityKind.MAP, k -> new ArrayList<>()).add(o);
		} else if (o instanceof MatchDTO) {
			this.removedEntities.computeIfAbsent(EntityKind.MATCH, k -> new ArrayList<>()).add(o);
		} //TODO ostatne entityKind
	}
	//----------------------------------------------------------------------
	private void insertNewEntities() {
		if (this.newEntities.containsKey(EntityKind.MAP)) {
			List<Object> list = this.newEntities.get(EntityKind.MAP);
			for (int i = 0; i < list.size(); i++) {
				MapDTO m = (MapDTO) list.get(i);
				MapDTO inserted = this.mapRepo.insert(m.getName());
				//list.set(i, inserted);
				m.setId(inserted.getId());
			}
		}
		
		//TODO opravit ked pridu ostatne atributy do zapasu
		if (this.newEntities.containsKey(EntityKind.MATCH)) {
			List<Object> list = this.newEntities.get(EntityKind.MATCH);
			for (int i = 0; i < list.size(); i++) {
				MatchDTO m = (MatchDTO) list.get(i);
				MatchDTO inserted = this.matchRepo.insert(m.getMap(), m.getScore());
				//list.set(i, inserted);
				m.setId(inserted.getId());
			}
		}
		
		//TODO ostatne entityKind
	}
	
	private void updateModifiedEntities() {
		if (this.modifiedEntities.containsKey(EntityKind.MAP)) {
			for (Object o : modifiedEntities.get(EntityKind.MAP)) {
				this.mapRepo.update((MapDTO) o);
			}
		}
		if (this.modifiedEntities.containsKey(EntityKind.MATCH)) {
			for (Object o : modifiedEntities.get(EntityKind.MATCH)) {
				this.matchRepo.update((MatchDTO) o);
			}
		}
		//TODO ostatne entityKind
	}
	
	private void deleteRemovedEntities() {
		if (this.removedEntities.containsKey(EntityKind.MAP)) {
			for (Object o : removedEntities.get(EntityKind.MAP)) {
				this.mapRepo.delete(((MapDTO) o).getId());
			}
		}
		if (this.removedEntities.containsKey(EntityKind.MATCH)) {
			for (Object o : removedEntities.get(EntityKind.MATCH)) {
				this.matchRepo.delete(((MapDTO) o).getId());
			}
		}
		//TODO ostatne entityKind
	}

	//----------------------------------------------------------------------
	private void rollback() {
		try {
			if (this.con.getCon() != null) {
				this.con.getCon().rollback();
			}
		} catch (SQLException e) {
			log.error("Rollback failed: " + e.getMessage());
		} finally {
			clearAll();
			//end();
		}
	}

	public void end() {
		if (this.con.getCon() != null) {
			try {
				this.con.getCon().setAutoCommit(true);
				this.con.getCon().close();
				log.info("Connection to DB successfully closed");
			} catch (SQLException e) {
				log.error("Failed to close connection to DB: " + e.getMessage());
			}
		}
	}

	private void clearAll() {
		this.newEntities.clear();
		this.modifiedEntities.clear();
		this.removedEntities.clear();
	}
}