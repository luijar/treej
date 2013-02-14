package net.atencio.util.event.tree;

public class NodeNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public NodeNotFoundException(String nodeId) {
		super(nodeId + " not found in tree. You must add it first before creating a path to it");
	}
}
