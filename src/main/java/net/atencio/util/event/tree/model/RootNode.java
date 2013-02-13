package net.atencio.util.event.tree.model;

public class RootNode<T> extends NodeImpl<T> {

	public RootNode(T value) {
		super("root", value);
	}	
}
