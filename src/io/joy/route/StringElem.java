package io.joy.route;

public class StringElem implements RouteElem {
	private String str;
	public StringElem(String str) {
		this.str = str;
	}
	
	@Override
	public String name() {
		return this.str;
	}

	@Override
	public boolean matchs(String elem) {
		return this.str.equals(elem);
	}

	@Override
	public boolean isParamter() {
		return false;
	}
	
	// 固定路径的优先级最高10
	@Override
	public int priority() {
		return 10;
	}
		
	@Override
	public boolean equals(Object o) {
		if (o == null || (o instanceof StringElem == false)) {
			return false;
		}
		
		return ((RouteElem) o).name().equals(this.name());
	}

	@Override
	public String toString() {
		return this.str;
	}
}
