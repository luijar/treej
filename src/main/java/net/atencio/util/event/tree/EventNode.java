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
public class EventNode<T> extends Observable implements Validateable, Identifiable, Comparable<String> {

	private final String id;
	private T value;
	private Set<EventNode<T>> nextNodes;
	
	public EventNode(String id, T value) {
		
		this.id = id;
		this.value = value;
		this.nextNodes = new HashSet<EventNode<T>>();
	}

	@Override
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
	
	/**
	 * This call to update, will make all path nodes underneath it ready to be notified.
	 * 
	 * @param value
	 * @return
	 */
	public EventNode<T> updateValue(T value) {
		
		this.value = value;
		propagateMarkAsChanged(this);
		return this;
	}
	
	public void markAsChanged() {
		
		this.setChanged();
	}
	
	// Mark this node as changed (if it hadn't) and all next nodes
	private void propagateMarkAsChanged(EventNode<T> n) {
		
		if(!n.hasChanged()) {
			n.markAsChanged();
			if(n.getDepth() != 0) {
				for(EventNode<T> nx: n.nextNodes) {
					propagateMarkAsChanged(nx);	
				}			
			}	
		}
	}
	
	@Override
	public boolean isValid() {
		
		return this.id != null && !this.id.isEmpty();
	}
	
	@Override
	public int compareTo(String oId) {
		
		return this.id.compareTo(oId);
	}
	
	public boolean addNext(EventNode<T> next) {
		
		return this.nextNodes.add(next);
	}
	
	Set<EventNode<T>> getNextNodes() {
		
		return this.nextNodes; 
	}
	
	public static <T> EventNode<T> build(String id, T value, Observer observer) {
		
		EventNode<T> n = new EventNode<T>(id, value);
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
		EventNode<T> other = (EventNode<T>) obj;
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
