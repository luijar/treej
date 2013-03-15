package net.atencio.util.event.tree;

/**
 * Interface used to build a dependency path from several source nodes to a destination node
 * 
 * @author luijar
 *
 */
public interface PathBuilder {

	public PathBuilder from(String... from);
	
	public PathBuilder to(String to);
	
	public void add() throws NodeNotFoundException;
}
