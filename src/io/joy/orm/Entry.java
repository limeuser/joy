package io.joy.orm;

import java.util.HashMap;
import java.util.Map;

public class Entry {
	private Schema schema;
	private Map<String, Object> map;
	
	public Entry(Schema schema) {
		this.schema = schema;
		this.map = new HashMap<String, Object>();
	}
	
	public Object get(String key) {
		return this.map.get(key);
	}
	
	public Entry put(String key, Object value) {
		if (this.schema.containsField(key) == false) {
			throw new RuntimeException("找不到field");
		}
		
		this.map.put(key, value);
		return this;
	}
	
	public String check() {
		return this.schema.check(this.map);
	}
}
