package main.java.net.atencio.event.tree.model;

import java.util.List;


/**
 * Abstract Node class
 * 
 * @author luijar
 *
 */
public interface Node {

	String getId();
	
	Node getPrevious();
	
	List<Node> getNext();
	
	boolean isRoot();
}
