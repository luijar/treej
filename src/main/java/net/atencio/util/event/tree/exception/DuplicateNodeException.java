package net.atencio.util.event.tree.exception;

/**
 * Duplicate nodes not allowed in tree
 * 
 * @author luijar
 *
 */
public class DuplicateNodeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DuplicateNodeException(String nodeId) {
		super("Duplicate Node Id not allowed: " + nodeId);
	}
}
