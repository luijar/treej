package net.atencio.util.event.tree.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Node class that carries objects as value 
 * 
 * @author luijar
 *
 * @param <T> Type of value to carry within this node
 */
public class NodeImpl<T> implements Node<T>{

	private final String id;
	private final T value;
	private NodeImpl<T> previous;
	private List<NodeImpl<T>> next;
		
	public NodeImpl(String id, T value) {
		this.id = id;
		this.value = value;
		this.next = new ArrayList<NodeImpl<T>>();
	}

	public String getId() {
		return this.id;
	}

	public boolean isRoot() {
		return this.previous == null;
	}
	
	public boolean isTerminal() {
		return this.next.isEmpty();
	}
	
	public T getValue() {
		return value;
	}
	
	@Override
	public boolean isValid() {
		return this.id != null && !this.id.isEmpty();
	}
	
	// used internally to navigate through the tree
	NodeImpl<T> getPrevious() {
		return this.previous;
	}

	// used internally to navifate through the tree
	List<NodeImpl<T>> getNext() {
		return Collections.unmodifiableList(this.next);
	}

	@Override
	public int compareTo(String oId) {
		return this.id.compareTo(oId);
	}
}
