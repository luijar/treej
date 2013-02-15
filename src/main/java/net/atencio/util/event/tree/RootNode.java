package net.atencio.util.event.tree;

/**
 * Special node whose ID is "root" 
 * 
 * @author luijar
 *
 * @param <T>
 */
public class RootNode<T> extends EventNode<T> {

	public RootNode(T value) {
		super("root", value);
	}	
}
