package io.joy.route;

public class PlaceholdElem implements RouteElem {
	private String key;
	public PlaceholdElem(String str) {
		this.key = str;
	}
	
	@Override
	public String name() {
		return this.key;
	}

	@Override
	public boolean matchs(String elem) {
		return true;
	}

	@Override
	public boolean isParamter() {
		return true;
	}
	
	@Override
	public int priority() {
		return 2;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null || (o instanceof PlaceholdElem == false)) {
			return false;
		}
		
		return ((RouteElem) o).name().equals(this.name());
	}

	@Override
	public String toString() {
		return "{" + this.name() + "}";
	}
}
