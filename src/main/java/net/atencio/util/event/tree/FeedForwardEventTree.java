package net.atencio.util.event.tree;

import java.util.Collection;

/**
 * Feed-forward Main Event Tree interface. Forms a graph with directed edge From source nodes to target nodes.
 * Any happens that happen in source nodes get propagated (if so desired) to child nodes.  
 * 
 * @author luijar
 */
public interface FeedForwardEventTree<T> extends Collection<Node<T>> {

	/**
	 * Set root of tree. The root node will automatically get mapped with an ID of "root"
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
	 * Depth of tree from the root. Note: this is different from the size of the tree which counts 
	 * how many nodes are in it.
	 * 
	 * @return
	 */
	int depth();
	
	/**
	 * Generate an event on a specific node and propagate
	 * 
	 * @see int generateEventOn(String nodeId, T change, boolean propagate) throws NodeNotFoundException
	 */
	int generateEventOn(String nodeId, T change) throws NodeNotFoundException;
	
	/**
	 * Generate an event on a specific node
	 * 
	 * @param nodeId   The source node id
	 * @param change   The changed value to communicate
	 * @return The count of nodes that were notified
	 * @throws NodeNotFoundException 
	 */
	int generateEventOn(String nodeId, T change, boolean propagate) throws NodeNotFoundException;
}
