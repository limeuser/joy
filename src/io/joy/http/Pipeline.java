package io.joy.http;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pipeline implements Handler {
	private List<Handler> handlers = new ArrayList<Handler>();
	
	public Pipeline() {}
	
	public Pipeline(Handler ...handlers) {
		for (Handler handler : handlers) {
			this.handlers.add(handler);
		}
	}
	
	public Pipeline(Collection<Handler> handlers) {
		this.handlers.addAll(handlers);
	}
	
	public Pipeline append(Handler handler) {
		this.handlers.add(handler);
		return this;
	}
	
	@Override
	public Connection handle(Connection conn) {
		for (Handler h : this.handlers) {
			if (conn.isHalted()) {
				return conn;
			}
			conn = h.handle(conn);
		}
		
		return conn;
	}
}
