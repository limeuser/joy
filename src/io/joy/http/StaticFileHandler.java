package io.joy.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class StaticFileHandler implements Handler {
	private String fileName;
	private byte[] content;
	
	public StaticFileHandler(String fileName) {
		this.fileName = fileName;
		
		File file = new File(this.fileName);
		FileInputStream reader;
		try {
			reader = new FileInputStream(file);
			reader.read(this.content);
		} catch (IOException e) {
			
		}
	}
	
	@Override
	public Connection handle(Connection conn) {
		File f = new File(fileName);
		conn.sendFile(200, f, 0L, f.length());
		return conn;
	}
}
