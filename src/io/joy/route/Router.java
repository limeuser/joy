package io.joy.route;

import java.util.Map;

import io.joy.http.Connection;
import io.joy.http.Handler;
import io.joy.http.Method;

public interface Router extends Handler {
	Router setNoFoundRouteHandler(Handler handler);
	
	Router addRoute(Method method, String route, Handler handler);
	default Handler getHandler(Method method, String path) {
		return getHandler(method, path, null);
	}
	
	Handler getHandler(Method method, String path, Map<String, String> pathParams);
	
	@Override
	default
	Connection handle(Connection conn) {
		return getHandler(conn.getMethod(), conn.getPath()).handle(conn);
	}
}