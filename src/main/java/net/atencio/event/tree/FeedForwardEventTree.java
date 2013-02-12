package net.atencio.event.tree;

import net.atencio.event.tree.model.Node;

/**
 * Main Event Tree interface
 * 
 * @author luijar
 */
public interface FeedForwardEventTree {

	<T> FeedForwardEventTree createRootNode(Node<T> root);
	
	<T> FeedForwardEventTree addNode(Node<T> n, String... ids);
	
	void buildTree();	
}
