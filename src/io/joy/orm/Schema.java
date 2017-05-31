package io.joy.orm;

import java.util.HashMap;
import java.util.Map;

public abstract class Schema {
	public String name;
	private Map<String, Field<?>> fields = new HashMap<String, Field<?>>();
	
	public Schema field(Field<?> field) {
		this.fields.put(field.name(), field);
		return this;
	}
	
	public boolean containsField(String name) {
		return this.fields.containsKey(name);
	}
	
	public Map<String, Field<?>> fields() {
		return this.fields;
	}
	
	@SuppressWarnings("unchecked")
	public String check(Map<String, Object> entry) {
		StringBuilder error = new StringBuilder();
		
		for (String name : this.fields().keySet()) {
			@SuppressWarnings("rawtypes")
			Field field =  this.fields().get(name);
			try { 
				String r = field.check(entry.get(name));
				if (r != Check.ok) {
					error.append(r).append("\r\n");
				}
			} catch (ClassCastException e) {
				error.append(String.format("%s.%s类型错误，field type is %s, value type is %s\r\n", 
						this.name, 
						name, 
						field.getClass().getName(), 
						entry.get(name).getClass().getName()));
			}
		}
		
		if (error.length() > 0) {
			return error.toString();
		} else {
			return Check.ok;
		}
	}
	
	public abstract Schema define();
}
