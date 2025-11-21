package app.utilities;

import dal.EntityKind;

/**
 * Observer design patter for automatic loggs.
 */
public interface Listener {
	/**
	 * Called when a new entity has been successfully created.
	 * @param id id of the entity
	 * @param entity kind of the entity (MAP, MATCH,...)
	 * @param caster name of the caster who created the entity for log
	 */
	void onCreated(int id, EntityKind entity, String caster);
	
	/**
	 * Called when an entity has been successfully updated.
	 * @param id id of the entity
	 * @param entity kind of the entity (MAP, MATCH,...)
	 * @param caster name of the caster who deleted the entity for log
	 */
	void onDeleted(int id, EntityKind entity, String caster);
	
	/**
	 * Called when an entity has been successfully deleted.
	 * @param id id of the entity
	 * @param entity kind of the entity (MAP, MATCH,...)
	 * @param caster name of the caster who modified the entity for log
	 */
	void onUpdated(int id, EntityKind entity, String caster);
}