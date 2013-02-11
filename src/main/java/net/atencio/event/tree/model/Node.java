package main.java.net.atencio.event.tree.model;

import java.io.Serializable;

/**
 * Abstract Node class
 * 
 * @author luijar
 *
 */
public abstract class Node implements Serializable {

	
	private Node parent;
	
	public Node getParent() {
		return parent;
	}
	
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	
	
	private static final long serialVersionUID = 1L;
}
