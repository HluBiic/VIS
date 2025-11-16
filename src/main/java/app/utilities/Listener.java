package app.utilities;

import dal.EntityKind;

public interface Listener {
	void onCreated(int id, EntityKind entity, String caster);
}
