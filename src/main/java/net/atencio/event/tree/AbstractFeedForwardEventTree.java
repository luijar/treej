package main.java.net.atencio.event.tree;

import java.util.HashMap;
import java.util.Map;

import main.java.net.atencio.event.tree.model.Node;

abstract class AbstractFeedForwardEventTree {

	protected Map<String, Node> nodeMap; 
	
	public AbstractFeedForwardEventTree() {
		
		this.nodeMap = new HashMap<String, Node>();
	}
}
