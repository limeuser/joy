package io.joy.test;

import io.joy.Web;
import io.joy.http.Connection;
import io.joy.http.DefaultNoRouteHandler;
import io.joy.http.EmptyHandler;
import io.joy.http.Handler;
import io.joy.http.Method;
import io.joy.http.netty.NettyHttpServer;
import io.joy.route.Router;
import io.joy.route.TreeRouter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;

public class Test {
	public static void main(String[] args)  {
		//testTreeRouter();
		
		try {
			testWeb();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testWeb() throws Exception {
		Router router = new TreeRouter(DefaultNoRouteHandler.Instance);
		router.addRoute(Method.GET, "/", new Handler() {
			@Override
			public Connection handle(Connection conn) {
				FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer("hello".getBytes()));
	            response.headers().set("content-type", "text/plain");
	            response.headers().set("content-length", response.content().readableBytes());
				conn.write(response);
				conn.flush();
				return conn;
			}
		});
		
		Web web = new Web();
		web.append(router);
		
		NettyHttpServer server = new NettyHttpServer();
		server.start(7002, web);
	}
	
	public static void testTreeRouter() {
		Handler handler0 = new EmptyHandler();
		Handler handler1 = new EmptyHandler();
		Handler handler2 = new EmptyHandler();
		Handler handler3 = new EmptyHandler();
		
		Router router = new TreeRouter(DefaultNoRouteHandler.Instance);
		
		router.addRoute(Method.GET, "/user/list", handler1)
		   	  .addRoute(Method.GET, "/user/{name}", handler2)
		      .addRoute(Method.GET, "/user/xx/insert", handler3)
		      .addRoute(Method.GET, "/", handler0);
		
		//System.out.println(router.toString());
		//System.out.println(router.getHandler(Method.GET, "/user/xxx"));
		
		assert router.getHandler(Method.GET, "/") == handler0;
		assert router.getHandler(Method.GET, "/user/list") == handler1;
		assert router.getHandler(Method.GET, "/user/zhang") == handler2;
		assert router.getHandler(Method.GET, "/user/zhang/xx") == DefaultNoRouteHandler.Instance;
		assert router.getHandler(Method.GET, "/user/xx/insert") == handler3;
	}
	
	public static void testOrm(Web web) {
		/*
		web.addSchema(new User());
		
		Oracle oracle = new Oracle();
		
		oracle.create(web.schema(User.myname));
		
		Entry entry = new Entry(web.schema(User.myname));
		entry.put("user", "zhangsan");
		entry.put("email", "xxx@163.com");
		
		String result = entry.check();
		if (result != Check.ok) {
			System.out.println(result);
		}
		
		oracle.insert(entry);
		*/
	}
}
