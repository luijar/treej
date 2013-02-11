package main.java.net.atencio.event.tree;

import main.java.net.atencio.event.tree.model.Node;

/**
 * 
 * @author luijar
 */
public class FeedForwardEventTreeImpl implements FeedForwardEventTree {
	
	public FeedForwardEventTreeImpl() {
		super();
	}
	
	@Override
	public void addNode(Node n) {
		
		if(n.getId() == null || n.getId().isEmpty()) {
			throw new IllegalArgumentException("Valid id expected. Got: " + n.getId());
		}
		
		
	}
	
}
