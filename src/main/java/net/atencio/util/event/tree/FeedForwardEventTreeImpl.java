package net.atencio.util.event.tree;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import net.atencio.util.event.tree.model.Node;
import net.atencio.util.event.tree.model.NodeImpl;
import net.atencio.util.event.tree.model.RootNode;

public class FeedForwardEventTreeImpl<T> implements FeedForwardEventTree<T> {

	private NodeImpl<T> root;
	private Map<String, Node<T>> cache;
	
	private static final Logger LOGGER = Logger.getLogger(FeedForwardEventTreeImpl.class.getName());
	
	public FeedForwardEventTreeImpl(int initCapacity) {
		
		this.cache = new HashMap<String, Node<T>>(initCapacity);
	}
	
	public FeedForwardEventTreeImpl() {
		
		this.cache = new HashMap<String, Node<T>>();
	}
	
	@Override
	public boolean setRoot(RootNode<T> root) {
		
		if(this.root != null) {
			throw new IllegalStateException("Root object already exists. Cannot replace it");
		}
		this.root = root;	
		return this.cache.put(root.getId(), root) == null;		
	}
	
	
	@Override
	public boolean addToPath(Node<T> node, String... sourceNodeIds) throws NodeNotFoundException { 
		
		// Add node and maps
		NodeImpl<T> nodeImpl = (NodeImpl<T>)node;
		if(sourceNodeIds != null) {
			for(String sourceId: sourceNodeIds) {
				NodeImpl<T> source = (NodeImpl<T>)this.cache.get(sourceId);
				
				if(source == null) {
					throw new NodeNotFoundException(sourceId);
				}
				
				if(!source.addNext(nodeImpl)) {
					LOGGER.warning("Skipped path from source [" + sourceId + "] to target [" + node.getId() + "]. Already exists");
				}
			}	
		}
		// Add node into datastructure
		return this.add(node);
	}
	
	@Override
	public void addPath(String targetNodeId, String... sourceNodeIds) throws NodeNotFoundException {
		
		NodeImpl<T> nodeImpl = (NodeImpl<T>)this.cache.get(targetNodeId);
		
		if(nodeImpl != null) {
			
			if(sourceNodeIds != null) {
				for(String sourceId: sourceNodeIds) {
					NodeImpl<T> source = (NodeImpl<T>)this.cache.get(sourceId);
					
					if(source == null) {
						throw new NodeNotFoundException(sourceId);
					}
					
					if(!source.addNext(nodeImpl)) {			
						LOGGER.warning("Skipped path from source [" + sourceId + "] to target [" + targetNodeId + "]. Already exists");
					}
				}	
			}
		}
	}
	
	@Override
	public void clear() {
		this.root = null;
		this.cache.clear();
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean contains(Object o) {
		if(!(o instanceof Node)) {
			throw new ClassCastException("Object must be a class of Node");
		}
		Node<T> node = (Node<T>)o;
		return this.cache.containsKey(node.getId());
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
		
		return this.cache.size();
	}

	@Override
	public boolean isEmpty() {
		
		return this.cache.isEmpty();
	}

	@Override
	public boolean add(Node<T> e) {
		
		if(e == null || !e.isValid()) {
			throw new IllegalArgumentException("Valid node expected. Got " + e);
		}
		
		if(this.cache.containsKey(e.getId())) {
			LOGGER.warning("Replacing node with id [" + e.getId() + "]");
		}
		
		return this.cache.put(e.getId(), e) == null;
	}

	@Override
	public boolean addAll(Collection<? extends Node<T>> c) {
		
		
		
		return false;
	}

	@Override
	public Iterator<Node<T>> iterator() {
		
		return this.cache.values().iterator();
	}

	@Override
	public Object[] toArray() {
		
		return this.cache.values().toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		
		return this.cache.values().toArray(a);
	}

	@Override
	public int getDepth() {
		
		return this.root == null ? 0 : this.root.getNext().size() + 1;
	}
	
	@Override
	public String toString() {
		return "FeedForwardEventTreeImpl [depth=" + this.getDepth() + ", elements=" + this.toArray() + "]";
	}
}
