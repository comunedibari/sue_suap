package it.wego.cross.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionAttributeMap {

	Map<UUID, Object> map;
	
	public SessionAttributeMap() {
		this.map = new HashMap<>();
	}

	public void put(Object value) {
		this.put(null, value);
	}

	public void put(String id, Object value) {
		UUID uuid = UUID.fromString(id);
		putUuid(uuid, value);
	}
	
	public Object get() {
		return get(null);
	}
	public Object get(String id) {
		return this.map.get(UUID.fromString(id));
	}

	public void remove(String id) {
		this.map.remove(UUID.fromString(id));
	}
	
	private void putUuid(UUID uuid, Object session) {
		this.map.put(uuid, session);
	}
}
