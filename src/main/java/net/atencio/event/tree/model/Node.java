package net.atencio.event.tree.model;

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
public class Node<T> {

	private String id;
	private Node<T> previous;
	private List<Node<T>> next;
	private T value;
	
	Node() {
		this.next = new ArrayList<Node<T>>();
	}
	
	public String getId() {
		return this.id;
	}

	public Node<T> getPrevious() {
		return this.previous;
	}

	public List<Node<T>> getNext() {
		return Collections.unmodifiableList(this.next);
	}

	public boolean isRoot() {
		return this.previous == null;
	}
	
	public T getValue() {
		return value;
	}
	
	public static class Builder<T> {
		private Node<T> _t = new Node<T>();
		
		public Builder<T> id(final String id) {
			_t.id = id;
			return this;
		}
		
		public Builder<T> previous(final Node<T> parent) {
			_t.previous = parent;
			return this;
		}
		
		public Builder<T> addNode(final Node<T> n) {
			_t.next.add(n);
			return this;
		}
		
		public Builder<T> addNodes(List<Node<T>> next) {
			_t.next = next;
			return this;
		}
		
		public Builder<T> value(T value) {
			_t.value = value;
			return this;
		}
		
		public Node<T> build() {
			if(_t.id == null || _t.id.isEmpty()) {
				throw new IllegalArgumentException("Node needs a valid Id");
			}
			return _t;
		}
	}
}
