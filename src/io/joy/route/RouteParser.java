package io.joy.route;

import io.joy.util.Bug;

public class RouteParser {
	public final static String[] EmptyElem = new String[0];
	
	public final static String check(String url) {
		if (url.length() == 0 || url.charAt(0) != '/') {
			throw new Bug("route path must start with '/', for example: /user/xxxx");
		}
		return url;
	}
	
	public final static String[] splitElem(String path) {
		check(path);
		
		// 删除开头的/
		path = path.substring(1);
		
		if (path.length() == 0) {
			return EmptyElem;
		}
		
		//去掉结尾的/
		if (path.length() > 1 && path.charAt(path.length() - 1) == '/') {
			path = path.substring(0, path.length() - 1);
		}
		
		return path.split("/");
	}
	
	public final static RouteElem[] parseRoute(String route) {
		String[] us = splitElem(route);
		
		RouteElem[] es = new RouteElem[us.length];
		
		for (int i = 0; i < us.length; i++) {
			es[i] = parseElement(us[i]);
		}
		
		return es;
	}
	
	public final static RouteElem parseElement(String str) {
		if (str.length() > 2 && 
			str.charAt(0) == '{' && 
			str.charAt(str.length() - 1) == '}') {
			return new PlaceholdElem(str.substring(1, str.length() - 1));
		}
		
		return new StringElem(str);
	}
}
