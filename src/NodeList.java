package src;

import java.util.*;

public class NodeList {
	
	static ArrayList<Node> nodes = new ArrayList<>();
	
	NodeList() {}
	
	public static void addNode(Node n) {
		nodes.add(n);
	}
	
	public static ArrayList<Node> getNodes() {
		return nodes;
	}
}
