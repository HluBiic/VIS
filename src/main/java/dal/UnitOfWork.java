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
import lombok.extern.log4j.Log4j2;

/**
 * Unit of Work design patter to coordinate and commit multiple changes as
 * a single transaction.
 */
@Log4j2
public class UnitOfWork {
	private DBConnection con;

	private Map<EntityKind, List<Object>> newEntities = new HashMap<>();
	private Map<EntityKind, List<Object>> modifiedEntities = new HashMap<>();
	private Map<EntityKind, List<Object>> removedEntities = new HashMap<>();
	
	private final MapRepository mapRepo = new MapRepository();
	private final MatchRepository matchRepo = new MatchRepository();
	
	/**
	 * Initializes the database connection and disables auto-commit to begin a transaction.
	 */
	public void begin() throws SQLException {
		this.con = DBConnection.getInstance();
		try {
			this.con.getCon().setAutoCommit(false);
			log.info("Transaction started");
		} catch (SQLException e) {
			throw new RuntimeException("Failed to begin transaction: " + e.getMessage());
		}
	}
	
	/**
	 * Executes all registered changes (insert, update, delete) and commits the transaction.
	 */
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
	/**
	 * Registers an entity to be inserted into the database upon commit.
	 */
	public void registerNew(Object o) {
		if (o instanceof MapDTO) {
			this.newEntities.computeIfAbsent(EntityKind.MAP, k -> new ArrayList<>()).add(o);
		} else if (o instanceof MatchDTO) {
			this.newEntities.computeIfAbsent(EntityKind.MATCH, k -> new ArrayList<>()).add(o);
		}
	}
	
	/**
	 * Registers an entity whose state has been modified and needs updating upon commit.
	 */
	public void registerModified(Object o) {
		if (o instanceof MapDTO) {
			this.modifiedEntities.computeIfAbsent(EntityKind.MAP, k -> new ArrayList<>()).add(o);
		} else if (o instanceof MatchDTO) {
			this.modifiedEntities.computeIfAbsent(EntityKind.MATCH, k -> new ArrayList<>()).add(o);
		}
	}
	
	/**
	 * Registers an entity to be deleted from the database upon commit.
	 */
	public void registerRemoved(Object o) {
		if (o instanceof MapDTO) {
			this.removedEntities.computeIfAbsent(EntityKind.MAP, k -> new ArrayList<>()).add(o);
		} else if (o instanceof MatchDTO) {
			this.removedEntities.computeIfAbsent(EntityKind.MATCH, k -> new ArrayList<>()).add(o);
		}
	}
	//----------------------------------------------------------------------
	/**
	 * Executes the SQL INSERT operations for all newly registered entities.
	 */
	private void insertNewEntities() {
		if (this.newEntities.containsKey(EntityKind.MAP)) {
			List<Object> list = this.newEntities.get(EntityKind.MAP);
			for (int i = 0; i < list.size(); i++) {
				MapDTO m = (MapDTO) list.get(i);
				MapDTO inserted = this.mapRepo.insert(m.getName());
				m.setId(inserted.getId());
			}
		}
		
		if (this.newEntities.containsKey(EntityKind.MATCH)) {
			List<Object> list = this.newEntities.get(EntityKind.MATCH);
			for (int i = 0; i < list.size(); i++) {
				MatchDTO m = (MatchDTO) list.get(i);
				MatchDTO inserted = this.matchRepo.insert(m.getTournament(), m.getTeamA(), m.getTeamB(), m.getMap(), m.getScore());
				m.setId(inserted.getId());
			}
		}
	}
	
	/**
	 * Executes the SQL UPDATE operations for all modified entities.
	 */
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
	
	/**
	 * Executes the SQL DELETE operations for all removed entities.
	 */
	private void deleteRemovedEntities() {
		if (this.removedEntities.containsKey(EntityKind.MAP)) {
			for (Object o : removedEntities.get(EntityKind.MAP)) {
				this.mapRepo.delete(((MapDTO) o).getId());
			}
		}
		if (this.removedEntities.containsKey(EntityKind.MATCH)) {
			for (Object o : removedEntities.get(EntityKind.MATCH)) {
				this.matchRepo.delete(((MatchDTO) o).getId());
			}
		}
	}
	//----------------------------------------------------------------------
	/**
	 * Rolls back the current transaction and clears the entity lists.
	 */
	private void rollback() {
		try {
			if (this.con.getCon() != null) {
				this.con.getCon().rollback();
			}
		} catch (SQLException e) {
			log.error("Rollback failed: " + e.getMessage());
		} finally {
			clearAll();
		}
	}

	/**
	 * Closes the database connection and reverts auto-commit to true.
	 */
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

	/**
	 * Clears all lists of new, modified, and removed entities.
	 */
	private void clearAll() {
		this.newEntities.clear();
		this.modifiedEntities.clear();
		this.removedEntities.clear();
	}
}