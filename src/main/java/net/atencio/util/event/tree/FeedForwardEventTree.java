package net.atencio.util.event.tree;

import java.util.Collection;

import net.atencio.util.event.tree.model.Node;
import net.atencio.util.event.tree.model.RootNode;

/**
 * Main Event Tree interface
 * 
 * @author luijar
 */
public interface FeedForwardEventTree<T> extends Collection<Node<T>> {

	/**
	 * Set root of tree. The root node will automatically get mapped with the root Id
	 * 
	 * @param root RootNode object with the first value in the tree
	 * @return
	 */
	boolean setRoot(RootNode<T> root);
	
	/**
	 * Add a node to the tree. This node will be notified from changes happening to other nodes
	 * 
	 * @param node The new node to add
	 * @param sourceNodeIds  The node ids this node depends on. Use "root" to specify a dependency on the root node
	 * @return
	 */
	boolean addToPath(Node<T> node, String... sourceNodeIds) throws NodeNotFoundException;
	
	/**
	 * Add a new event path. Use "root" to reference the root node
	 * 
	 * @param sourceNodeIds Source Node ids. Sources 
	 * @param targetNodeId  Target node id
	 */
	void addPath(String targetNodeId, String... sourceNodeIds) throws NodeNotFoundException;	
	
	/**
	 * Depth of tree
	 * 
	 * @return
	 */
	int getDepth();
}
