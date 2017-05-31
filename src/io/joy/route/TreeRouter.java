package io.joy.route;

import java.util.Map;

import io.joy.http.Handler;
import io.joy.http.Method;

public class TreeRouter implements Router {
	private Handler noRouteHandler;
	private RouteTree[] routeTreesByMethod = new RouteTree[Method.values().length];
	
	public TreeRouter(Handler noRouteHandler) {
		this();
		this.noRouteHandler = noRouteHandler;
	}
	
	public TreeRouter() {
		for (int i = 0; i < routeTreesByMethod.length; i++) {
			routeTreesByMethod[i] = new RouteTree();
		}
	}
	
	public TreeRouter addRoute(Method method, String path, Handler handler) {
		routeTreesByMethod[method.ordinal()].insert(path, handler);
		return this;
	}
	
	public Handler getHandler(Method method, String path, Map<String, String> paramters) {
		Handler handler = this.routeTreesByMethod[method.ordinal()].find(RouteParser.splitElem(path), paramters);
		if (handler == null) {
			return noRouteHandler;
		} else {
			return handler;
		}
	}

	@Override
	public Router setNoFoundRouteHandler(Handler handler) {
		this.noRouteHandler = handler;
		return this;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (Method method : Method.values()) {
			str.append(method.name())
			   .append("\r\n")
			   .append(routeTreesByMethod[method.ordinal()].toString())
			   .append("\r\n");
		}
		return str.toString();
	}
}