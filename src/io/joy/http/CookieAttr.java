package io.joy.http;

public class CookieAttr {
	public String domain;// - the domain the cookie applies to
	public int maxAge;// - the cookie max-age, in seconds. Providing a value for this option will set both the max-age and expires cookie attributes
	public String path;// - the path the cookie applies to
	public boolean httpOnly;// - when false, the cookie is accessible beyond http
	public boolean secure;// - if the cookie must be sent only over https. Defaults to true when the connection is https
	public String extra;// - string to append to cookie. Use this to take advantage of non-standard cookie attributes.
}
