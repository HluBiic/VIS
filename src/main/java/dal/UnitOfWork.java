package dal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dal.repo.MapRepository;
import dto.MapDTO;
import dto.MatchDTO;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

public class UnitOfWork {

}


/*
@Log4j2
public class UnitOfWork {
	private DBConnection conn;
	
	private Map<EntityKind, List<Object>> newEntities = new HashMap<>();
	private Map<EntityKind, List<Object>> modifiedEntities = new HashMap<>();
	private Map<EntityKind, List<Object>> removedEntities = new HashMap<>();
	
	@Getter
	private final MapRepository mapRepo = new MapRepository();
	@Getter
	private final MatchRepository matchRepo = new MatchRepository();
	
	public void begin() throws SQLException {
		this.conn = DBConnection.getInstance();
		try {
			this.conn.getCon().setAutoCommit(false);
			log.info("Transaction started.");
		} catch (SQLException e) {
			throw new RuntimeException("Failed to begin transaction: " + e.getMessage());
		}
	}
	
	
	public void commit() {
		try {
			if (this.conn.getCon() == null) {
				begin();
			}
			
			insertNewEntities();
			updateModifiedEntities();
			deleteRemovedEntities();
			
			
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
		}
	}
	public void registerModified(Object o) {
		if (o instanceof MapDTO) {
			this.modifiedEntities.computeIfAbsent(EntityKind.MAP, k -> new ArrayList<>()).add(o);
		} else if (o instanceof MatchDTO) {
			this.modifiedEntities.computeIfAbsent(EntityKind.MATCH, k -> new ArrayList<>()).add(o);
		}
	}
	public void registerRemoved(Object o) {
		if (o instanceof MapDTO) {
			this.removedEntities.computeIfAbsent(EntityKind.MAP, k -> new ArrayList<>()).add(o);
		} else if (o instanceof MatchDTO) {
			this.removedEntities.computeIfAbsent(EntityKind.MATCH, k -> new ArrayList<>()).add(o);
		}
	}
	//----------------------------------------------------------------------
	private void insertNewEntities() {
		if (this.newEntities.containsKey(EntityKind.MAP)) {
			List<Object> list = this.newEntities.get(EntityKind.MAP);
			for (int i = 0; i < list.size(); i++) {
				MapDTO m = (MapDTO) list.get(i);
				MapDTO inserted = this.mapRepo.add(m);
				list.set(i, inserted);
			}
		}
		if (this.newEntities.containsKey(EntityKind.MATCH)) {
			List<Object> list = this.newEntities.get(EntityKind.MATCH);
			for (int i = 0; i < list.size(); i++) {
				MatchDTO m = (MatchDTO) list.get(i);
				MatchDTO inserted = this.matchRepo.add(m);
				list.set(i, inserted);
			}
		}
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
	}
	
	private void deleteRemovedEntities() {
	    if (this.removedEntities.containsKey(EntityKind.MAP)) {
	        for (Object o : removedEntities.get(EntityKind.MAP)) {
	            this.mapRepo.deleteById(((MapDTO) o).getId());
	        }
	    }
	    if (this.removedEntities.containsKey(EntityKind.MATCH)) {
	        for (Object o : removedEntities.get(EntityKind.MATCH)) {
	            this.matchRepo.deleteById(((MatchDTO) o).getId());
	        }
	    }
	}

	//----------------------------------------------------------------------
	public void rollback() {
        try {
            if (this.conn.getCon() != null) this.conn.getCon().rollback();
            log.info("UnitOfWork: Transaction rolled back.");
        } catch (SQLException e) {
            log.error("Rollback failed: " + e.getMessage());
        } finally {
            clearAll();
            end();
        }
	}
	
	public void clearAll() {
		this.newEntities.clear();
		this.modifiedEntities.clear();
		this.removedEntities.clear();
	}
	
	
	public void end() {
		if (this.conn.getCon() != null) {
			try {
				this.conn.getCon().setAutoCommit(true);
				this.conn.getCon().close();
				log.info("Connection to DB successfully closed");
			} catch (SQLException e) {
				log.error("Failed to close connection to DB: " + e.getMessage());
			}
		}
	}
}
*/