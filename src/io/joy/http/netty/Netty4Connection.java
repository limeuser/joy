package io.joy.http.netty;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import io.joy.http.ConfigSessionAction;
import io.joy.http.Connection;
import io.joy.http.CookieAttr;
import io.joy.http.Method;
import io.joy.util.Bug;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpScheme;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.cookie.ClientCookieDecoder;
import io.netty.handler.codec.http.cookie.Cookie;

public class Netty4Connection implements Connection {
	private boolean halted = false;
	private FullHttpRequest request;
	private ChannelHandlerContext ctx;
	private Map<Object, Object> attr = new HashMap<Object, Object>();
	
	private Map<String, List<String>> urlParamters;
	
	public Netty4Connection(ChannelHandlerContext ctx, FullHttpRequest request) {
		this.ctx = ctx;
		this.request = request;
	}
	
	private final static Method mapMethod(HttpMethod method) {
		if (method == HttpMethod.CONNECT) {
			return Method.CONNECT;
		} else if (method == HttpMethod.DELETE) {
			return Method.DELETE;
		} else if (method == HttpMethod.GET) {
			return Method.GET;
		}else if (method == HttpMethod.HEAD) {
			return Method.HEAD;
		}else if (method == HttpMethod.OPTIONS) {
			return Method.OPTIONS;
		}else if (method == HttpMethod.PATCH) {
			return Method.PATCH;
		}else if (method == HttpMethod.POST) {
			return Method.POST;
		}else if (method == HttpMethod.PUT) {
			return Method.PUT;
		}else if (method == HttpMethod.TRACE) {
			return Method.TRACE;
		}else {
			throw new Bug("unknown http method: " + method.name());
		}
	}

	@Override
	public Map<String, String> getRequestCookies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getResponseCookies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getParamters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getQueryParamters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getBodyParamters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getPathParamters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCookie(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRequestCookie(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getResponseCookie(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getParamter(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUrlParamter(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPostParamter(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPathParamter(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String host() {
		return this.request.headers().get(HttpHeaderNames.HOST);
	}

	@Override
	public Method method() {
		return mapMethod(this.request.method());
	}

	private String[] pathElems;
	@Override
	public String[] pathElems() {
		if (pathElems == null) {
			pathElems = path().substring(1).split("/");
		}
		
		return pathElems;
	}

	@Override
	public String path() {
		return this.request.uri();
	}

	@Override
	public InetSocketAddress peer() {
		return (InetSocketAddress) this.ctx.channel().remoteAddress();
	}

	@Override
	public Iterator<Entry<CharSequence, CharSequence>> requestHeaders() {
		return this.request.headers().iteratorCharSequence();
	}

	@Override
	public String scheme() {
		return this.request.protocolVersion().protocolName();
	}

	private String queryString;
	@Override
	public String queryString() {
		if (queryString == null) {
			int limiter = this.path().indexOf('?');
			if (limiter > 0) {
				queryString = this.path().substring(limiter + 1);
			} else {
				queryString = "";
			}
		}
		
		return this.queryString;
	}

	private byte[] responseBody;
	
	@Override
	public byte[] responseBody() {
		return this.responseBody;
	}

	private String responseCharset;
	@Override
	public String responseCharset() {
		return this.responseCharset;
	}

	@Override
	public Map<String, String> responseHeaders() {
		return null;
	}

	private int status = 200;
	@Override
	public int status() {
		return this.status;
	}

	private Map<String, Object> assigns;
	@Override
	public Map<String, Object> assigns() {
		return this.assigns;
	}

	private Map<String, Object> privates;
	@Override
	public Map<String, Object> privates() {
		return this.privates;
	}

	private boolean isHalted;
	@Override
	public boolean isHalted() {
		return this.isHalted;
	}

	@Override
	public Connection halt() {
		this.isHalted = true;
		return this;
	}

	@Override
	public Connection assign(String key, Object value) {
		this.assigns.put(key, value);
		return this;
	}

	@Override
	public Connection sendChunkedHeaders(int status) {
		FullHttpResponse response = new DefaultFullHttpResponse(this.request.protocolVersion(), HttpResponseStatus.valueOf(status));
		this.ctx.write(response);
		return this;
	}

	@Override
	public Connection sendChunkedBody(byte[] body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection clearSession() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection configSession(ConfigSessionAction action) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection deleteRequestHeader(String key) {
		this.request.headers().remove(key);
		return this;
	}

	@Override
	public Connection deleteResponseCookie(String key, CookieAttr attr) {
		return this;
	}

	@Override
	public Connection deleteResponseHeader(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection deleteSession(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	private Set<Cookie> cookies;
	
	@Override
	public Connection fetchCookies(CookieAttr attr) {
        String value = request.headers().get(HttpHeaderNames.COOKIE);
        if (value == null) {
            cookies = Collections.emptySet();
        } else {
        	for (String str : value.split(";")) {
        		Cookie cookie = ClientCookieDecoder.LAX.decode(str.trim());
        		cookies.add(cookie);
        	}
        }
        
        return this;
	}
	
	@Override
	public Set<Cookie> getCookies() {
		return 
	}

	private QueryStringDecoder queryStringDecoder;
	
	@Override
	public Connection fetchQueryParams() {
		queryStringDecoder = new QueryStringDecoder(this.request.uri());
		return this;
	}

	@Override
	public Connection fetchSession(CookieAttr attr) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public String getRequestHeader(String key) {
		return this.request.headers().get(key);
	}

	@Override
	public String[] getResponseHeader(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSession(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection mergeResponseHeader(Map<String, String> headers) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection putPrivate(String key, Object value) {
		this.privates.put(key, value);
		return this;
	}

	@Override
	public Connection putRequestHeader(String key, String value) {
		this.request.headers().add(key, value);
		return this;
	}

	private String responseContentType;
	@Override
	public Connection putResponseContentType(String contentType, String charset) {
		this.responseContentType = contentType;
		this.responseCharset = charset;
		return this;
	}

	@Override
	public Connection putResponseCookie(String key, String value, CookieAttr attr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection putResponseHeader(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection putSession(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection putStatus(int status) {
		this.status = status;
		return this;
	}

	@Override
	public byte[] readBody(int length, int readLength, int readTimeout) {
		this.ctx.read();
		this.ctx.
	}

	@Override
	public Connection setResponse(int status, byte[] body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection sendFile(int status, File file, long offset, long length) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection sendResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection sendResponse(int status, byte[] body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection updateRequestHeader(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection updateResponseHeader(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}
}
