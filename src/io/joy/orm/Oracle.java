package io.joy.orm;

import java.util.List;

public class Oracle implements Store {
	@Override
	public void create(Schema schema) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insert(Entry entry) {
		String result = entry.check();
		if (result != Check.ok) {
			return;
		}
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Entry> getAll(Schema schema) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void drop(String table) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Entry> select(String sql, Schema schema) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Entry entry) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(String sql) {
		// TODO Auto-generated method stub
		
	}

}
