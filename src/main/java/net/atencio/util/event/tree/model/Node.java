package net.atencio.util.event.tree.model;

/**
 * Idenfiable nodes that can hold a value. Comparable only by id
 * 
 * @author luijar
 *
 * @param <T> Type of value a Node can hold.
 */
public interface Node<T> extends Validateable, Comparable<String> {

	String getId();
	
	T getValue();
}
