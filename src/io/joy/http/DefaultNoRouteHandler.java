package io.joy.http;

public class DefaultNoRouteHandler implements Handler {
	private DefaultNoRouteHandler() {}
	public final static DefaultNoRouteHandler Instance = new DefaultNoRouteHandler();
	
	@Override
	public Connection handle(Connection conn) {
		conn.sendResponse(200, String.format("the url: %s not found", conn.path()).getBytes());
		return conn;
	}
}
