package net.atencio.util.event.tree;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import net.atencio.util.event.tree.model.Node;
import net.atencio.util.event.tree.model.RootNode;

public class FeedForwardEventTreeImpl<T> implements FeedForwardEventTree<T> {

	private Node<T> root;
	private Map<String, Node<T>> quickLookup;
	private int depth;
	
	private static final Logger LOGGER = Logger.getLogger(FeedForwardEventTreeImpl.class.getName());
	
	public FeedForwardEventTreeImpl(int initCapacity) {
		
		this.quickLookup = new HashMap<String, Node<T>>(initCapacity);
	}
	
	public FeedForwardEventTreeImpl() {
		
		this.quickLookup = new HashMap<String, Node<T>>();
	}
	
	@Override
	public boolean setRoot(RootNode<T> root) {
		if(this.root != null) {
			throw new IllegalStateException("Root object already exists. Cannot replace it");
		}
		this.root = root;	
		return this.quickLookup.put(root.getId(), root) == null;
	}
	
	
	@Override
	public boolean addToPath(Node<T> node, String... targetNodeIds) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean addPath(String sourceNodeId, String... targetNodeIds) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void clear() {
		this.root = null;
		this.quickLookup.clear();
		this.depth = 0;
	}

	@Override
	public boolean contains(Object o) {
		if(!(o instanceof Node)) {
			throw new ClassCastException("Object must be a class of Node");
		}
		Node<T> node = (Node<T>)o;
		return this.quickLookup.containsKey(node.getId());
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		
		return this.quickLookup.size();
	}

	@Override
	public boolean isEmpty() {
		
		return this.quickLookup.isEmpty();
	}

	@Override
	public boolean add(Node<T> e) {
		
		if(e == null || !e.isValid()) {
			throw new IllegalArgumentException("Valid node expected. Got " + e);
		}
		
		if(this.quickLookup.containsKey(e.getId())) {
			LOGGER.warning("Replacing node with id [" + e.getId() + "]");
		}
		
		return this.quickLookup.put(e.getId(), e) == null;
	}

	@Override
	public boolean addAll(Collection<? extends Node<T>> c) {
		
		
		
		return false;
	}

	@Override
	public Iterator<Node<T>> iterator() {
		
		return this.quickLookup.values().iterator();
	}

	@Override
	public Object[] toArray() {
		
		return this.quickLookup.values().toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		
		return this.quickLookup.values().toArray(a);
	}

	@Override
	public String toString() {
		return "FeedForwardEventTreeImpl [depth=" + depth + ", elements=" + this.toArray() + "]";
	}
}
