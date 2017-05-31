package io.joy.http;

public class LogHandler implements Handler {
	private String name;
	public LogHandler(String name) {
		this.name = name;
	}
	
	@Override
	public Connection handle(Connection con) {
		System.out.println(this.name);
		return con;
	}
	
	
	@Override
	public String toString() {
		return this.name;
	}
}
