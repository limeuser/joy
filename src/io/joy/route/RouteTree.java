package io.joy.route;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.joy.http.Handler;
import io.joy.util.Bug;

public class RouteTree {
	public class Node {
		RouteElem key;
		Handler value;
		
		Node parent;
		List<Node> children;
		
		String route() {
			if (this == tree) {
				return "/";
			}
			
			Node node = this;
			List<Node> nodes = new ArrayList<Node>();
			while (node != tree) {
				nodes.add(node);
				node = node.parent;
			}
			
			StringBuilder str = new StringBuilder();
			for (int i = nodes.size() - 1; i >= 0; i--) {
				str.append("/").append(nodes.get(i).key);
			}
			
			return str.toString();
		}
	}
	
	
	private Node tree = new Node();
	
	public RouteTree() {
		this.tree = new Node();
		this.tree.key = new StringElem("/");
	}
	
	public Handler find(String path, Map<String, String> paramters) {
		return find(RouteParser.splitElem(path), paramters);
	}
	
	public Handler find(String[] keys, Map<String, String> paramters) {
		int i = 0, tier = 0;
		Node node = this.tree;
		List<Node> nodes = this.tree.children;
		
		for (; nodes != null && i < nodes.size() && tier < keys.length; i++) {
			Node child = nodes.get(i);
			if (child.key.matchs(keys[tier])) {
				tier++;
				node = child;
				nodes = child.children;
				i = -1;
				
				if (paramters != null && child.key.isParamter()) {
					paramters.put(child.key.name(), keys[tier]);
				}
			}
		}
		
		if (keys.length != tier) {
			return null;
		} else {
			return node.value;
		}
	}
	
	// path的模式：/user/xxx
	public void insert(String route, Handler handler) {
		insert(route, RouteParser.parseRoute(route), handler);
	}
	
	public void insert(String route, RouteElem[] keys, Handler value) {
		if (keys.length == 0) {
			this.tree.value = value;
			return;
		}
		
		int i = 0, tier = 0;
		List<Node> nodes = this.tree.children;
		
		Node found = this.tree;
		for (; nodes != null && i < nodes.size() && tier < keys.length; i++) {
			Node child = nodes.get(i);
			if (child.key.equals(keys[tier])) {
				tier++;
				i = -1;
				nodes = child.children;
				found = child;
			}
		}
		
		// found the node by keys
		if (tier == keys.length && found.value != null) {
			throw new Bug(String.format("%s has been set: %s", route, found.value));
		} 
		
		if (nodes == null) {
			found.children = new ArrayList<Node>();
			nodes = found.children;
		}
		
		// insert remaining keys
		for (; tier < keys.length; tier++) {
			Node inserting = new Node();
			inserting.children = new ArrayList<Node>();
			inserting.key = keys[tier];
			
			// set handler when node is leaf
			if (tier == keys.length - 1) {
				inserting.value = value;
			}
			
			insetSiblingByPriority(nodes, inserting);
			inserting.parent = found;
			
			nodes = inserting.children;
			found = inserting;
		}
	}
	
	private void insetSiblingByPriority(List<Node> nodes, Node sibling) {
		int i = 0;
		
		for (; i < nodes.size(); i++) {
			Node node = nodes.get(i);
			if (node.key.priority() < sibling.key.priority()) {
				break;
			}
		}
		
		nodes.add(null);
		for (int j = nodes.size() - 1; j > i; j--) {
			nodes.set(j, nodes.get(j - 1));
		}
		
		nodes.set(i, sibling);
	}
	
	@Override
	public String toString() {
		return format(0, 0, tree, new StringBuilder()).toString();
	}
	
	private final static StringBuilder format(int tier, int index, Node node, StringBuilder str) {
		if (node.value != null) {
			str.append(format(tier, index, node)).append("\r\n");
		}
		if (node.children == null) {
			return str;
		}
		
		int i = 0;
		for (Node n : node.children) {
			format(tier + 1, i++, n, str);
		}
		
		return str;
	}
	
	private final static String format(int tier, int index, Node node) {
		return String.format("%d:%d {%s => %s}", tier, index, node.route(), node.value.getClass().getName());
	}
}