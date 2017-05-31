package io.joy.orm;

import java.util.List;

public interface Store {
	void create(Schema schema);
	void insert(Entry entry);
	List<Entry> getAll(Schema schema);
	void drop(String table);
	List<Entry> select(String sql, Schema schema);
	void update(Entry entry);
	void update(String sql);
}