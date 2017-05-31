package io.joy.http;

public interface Handler {
	Connection handle(Connection conn);
}