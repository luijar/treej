package net.atencio.util.event.tree.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Node class that carries objects as value. Two nodes are equal if they have the same ID, regardless of value.
 * 
 * @author luijar
 *
 * @param <T> Type of value to carry within this node
 */
public class NodeImpl<T> implements Node<T> {

	private final String id;
	private final T value;
	private Set<NodeImpl<T>> next;
		
	public NodeImpl(String id, T value) {
		this.id = id;
		this.value = value;
		this.next = new HashSet<NodeImpl<T>>();
	}

	public String getId() {
		return this.id;
	}

	public boolean isRoot() {
		return this.id.equals("root");
	}
	
	public T getValue() {
		return value;
	}
	
	@Override
	public boolean isValid() {
		return this.id != null && !this.id.isEmpty();
	}
	
	public boolean addNext(NodeImpl<T> next) {
		
		return this.next.add(next);
	}
	
	public Set<NodeImpl<T>> getNext() {
		return Collections.unmodifiableSet(this.next);
	}

	@Override
	public int compareTo(String oId) {
		return this.id.compareTo(oId);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NodeImpl<T> other = (NodeImpl<T>) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NodeImpl [id=" + id + ", value=" + value + "]";
	}
}
