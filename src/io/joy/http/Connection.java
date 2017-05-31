package io.joy.http;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public interface Connection {
	Map<String, String> getCookies();
	Map<String, String> getRequestCookies();
	Map<String, String> getResponseCookies();
	
	Map<String, String> getParamters();
	Map<String, String> getQueryParamters();
	Map<String, String> getBodyParamters();
	Map<String, String> getPathParamters();
	
	String getCookie(String key);
	String getRequestCookie(String key);
	String getResponseCookie(String key);
	
	String getParamter(String key);
	String getUrlParamter(String key);
	String getPostParamter(String key);
	String getPathParamter(String key);
	
	String host();
	Method method();
	String[] pathElems();
	String path();
	InetSocketAddress peer();
	Iterator<Entry<CharSequence, CharSequence>> requestHeaders();
	String scheme();
	String queryString();
	
	byte[] responseBody();
	String responseCharset();
	Map<String, String> responseHeaders();
	int status();
	
	Map<String, Object> assigns();
	Map<String, Object> privates();
	
	boolean isHalted();
	Connection halt();
	
	Connection assign(String key, Object value);
	Connection sendChunkedHeaders(int status);
	Connection sendChunkedBody(byte[] body);
	Connection clearSession();
	Connection configSession(ConfigSessionAction action);
	Connection deleteRequestHeader(String key);
	Connection deleteResponseCookie(String key, CookieAttr attr);
	Connection deleteResponseHeader(String key);
	Connection deleteSession(String key);
	Connection fetchCookies(CookieAttr attr);
	Connection fetchQueryParams();
	Connection fetchSession(CookieAttr attr);
	String getRequestHeader(String key);
	String getResponseHeader(String key);
	String getSession(String key);
	Connection mergeResponseHeader(Map<String, String> headers);
	Connection putPrivate(String key, Object value);
	Connection putRequestHeader(String key, String value);
	Connection putResponseContentType(String contentType, String charset);
	Connection putResponseCookie(String key, String value, CookieAttr attr);
	Connection putResponseHeader(String key, String value);
	Connection putSession(String key, String value);
	Connection putStatus(int status);
	byte[] readBody(int length, int readLength, int readTimeout);
	Connection setResponse(int status, byte[] body);
	
	Connection sendFile(int status, File file, long offset, long length);
	Connection sendResponse();
	Connection sendResponse(int status, byte[] body);
	Connection updateRequestHeader(String key, String value);
	Connection updateResponseHeader(String key, String value);
}
