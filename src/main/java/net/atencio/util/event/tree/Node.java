package net.atencio.util.event.tree;

import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

/**
 * Node class that carries objects as value. Two nodes are equal if they have the same ID, regardless of value.
 * 
 * @author luijar
 *
 * @param <T> Type of value to carry within this node
 */
public class Node<T> extends Observable implements Validateable, Comparable<String> {

	private final String id;
	private final T value;
	private Set<Node<T>> nextNodes;
	
	public Node(String id, T value) {
		
		this.id = id;
		this.value = value;
		this.nextNodes = new HashSet<Node<T>>();
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
	
	public int getDepth() {
		
		return this.nextNodes.size();
	}
	
	@Override
	public boolean isValid() {
		return this.id != null && !this.id.isEmpty();
	}
	
	@Override
	public int compareTo(String oId) {
		return this.id.compareTo(oId);
	}
	
	public boolean addNext(Node<T> next) {
		
		return this.nextNodes.add(next);
	}
	
	Set<Node<T>> getNextNodes() {
		
		return this.nextNodes; 
	}
	
	public static <T> Node<T> build(String id, T value, Observer observer) {
		Node<T> n = new Node<T>(id, value);
		n.addObserver(observer);
		return n;
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
		Node<T> other = (Node<T>) obj;
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
