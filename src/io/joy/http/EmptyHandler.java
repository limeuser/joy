package io.joy.http;

public class EmptyHandler implements Handler {
	@Override
	public Connection handle(Connection con) {
		return con;
	}
}
