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
	 * @param targetNodeIds  The node ids this node depends on
	 * @return
	 */
	boolean addToPath(Node<T> node, String... targetNodeIds);
	
	/**
	 * Add a new event path. 
	 * 
	 * @param id  Source Node id
	 * @param ids Target node id
	 * @return
	 */
	boolean addPath(String sourceNodeId, String... targetNodeIds);	
}
