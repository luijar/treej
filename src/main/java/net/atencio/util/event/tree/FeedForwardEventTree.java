package net.atencio.util.event.tree;

import java.util.Collection;

/**
 * Feed-forward Main Event Tree interface. Forms a graph with directed edge From source nodes to target nodes.
 * Any happens that happen in source nodes get propagated (if so desired) to child nodes.  
 * 
 * @author luijar
 */
public interface FeedForwardEventTree<T> extends Collection<EventNode<T>> {

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
	boolean addToPath(EventNode<T> node, String... sourceNodeIds) throws NodeNotFoundException;
	
	/**
	 * Add a new event path. Use "root" to reference the root node
	 * 
	 * @param sourceNodeIds Source Node ids. Sources 
	 * @param targetNodeId  Target node id
	 */
	PathBuilder newPath() throws NodeNotFoundException;	
	
	/**
	 * Depth of tree from the root. Note: this is different from the size of the tree which counts 
	 * how many nodes are in it.
	 * 
	 * @return
	 */
	int depth();
		
	/**
	 * Generate an event on a specific node
	 * 
	 * @param nodeId   The source node id
	 * @param context  Used to pass any context object that could be useful within the observers
	 * @param propagate Wheter to propagate event to entire tree under source node
	 * @return The count of nodes that were notified
	 * @throws NodeNotFoundException 
	 */
	int generateEventOn(String nodeId, Object context) throws NodeNotFoundException;
	
	/**
	 * @see int generateEventOn(String nodeId, T change) throws NodeNotFoundException
	 */
	int generateEventOn(EventNode<T> node, Object context) throws NodeNotFoundException;
	
	
	/**
	 * Generate an event on a specific node, propagate and acquire a trace of the events.
	 * 
	 * @param nodeId   The source node id
	 * @param context  Used to pass any context object that could be useful within the observers
	 * @return A trace of all the steps from the specified node
	 * @throws NodeNotFoundException 
	 */
	Trace generateTracedEventOn(String nodeId, Object context) throws NodeNotFoundException;
	
	/**
	 * 
	 * @see generateTracedEventOn(String nodeId, Object context) throws NodeNotFoundException
	 */
	Trace generateTracedEventOn(EventNode<T> node, Object context)	throws NodeNotFoundException;
}
