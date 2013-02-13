package net.atencio.util.event.tree.model;

import java.util.List;

/**
 * Helper class to extract package level information from Node
 * 
 * @author luijar
 *
 */
public class NodeInfoAccessor {

	public static <T> NodeImpl<T> getPrevious(final NodeImpl<T> node) {
		
		return node.getPrevious();
	}
	
	
	public static <T> List<NodeImpl<T>> getNext(final NodeImpl<T> node) {
		
		return node.getNext();
	}
}
