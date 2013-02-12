package net.atencio.event.tree;

import java.util.HashMap;
import java.util.Map;

import net.atencio.event.tree.model.Node;

abstract class AbstractFeedForwardEventTree implements FeedForwardEventTree {

	protected Map<String, Node<?>> nodeMap; 
	
	public AbstractFeedForwardEventTree() {
		
		this.nodeMap = new HashMap<String, Node<?>>();
	}
	
	@Override
	public <T> FeedForwardEventTree addNode(Node<T> n, String... ids) {
		
		if(n.getId() == null || n.getId().isEmpty()) {
			throw new IllegalArgumentException("Valid id expected. Got: " + n.getId());
		}		
		
		
		
		return this;
	}
	
	@Override
	public <T> FeedForwardEventTree createRootNode(Node<T> root) {
		
		
		return this;
	}
	
	@Override
	public void buildTree() {
		
	}
}
