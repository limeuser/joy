package io.joy.route;

public interface RouteElem {
	String name();
	boolean matchs(String pathElem);
	boolean isParamter();
	
	// 如果可以匹配多个路径，选择优先级最高的
	int priority();
}