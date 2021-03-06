package net.atencio.util.event.tree;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import net.atencio.util.event.tree.exception.DuplicateNodeException;
import net.atencio.util.event.tree.exception.NodeNotFoundException;

public class FeedForwardEventTreeImpl<T> implements FeedForwardEventTree<T> {

	private EventNode<T> root;
	private Map<String, EventNode<T>> nodeIdMap;
	private PathBuilderImpl pathBuilder;
	
	private static final Logger LOGGER = Logger.getLogger(FeedForwardEventTreeImpl.class.getName());
	
	public FeedForwardEventTreeImpl(int initCapacity) {
		
		this.nodeIdMap = new HashMap<String, EventNode<T>>(initCapacity);
		this.pathBuilder = new PathBuilderImpl();
	}
	
	public FeedForwardEventTreeImpl() {
		
		this(16);
	}
	
	@Override
	public boolean setRoot(RootNode<T> root) {
		
		if(this.root != null) {
			throw new IllegalStateException("Root object already exists. Cannot replace it");
		}
		this.root = root;
		return this.nodeIdMap.put(root.getId(), root) == null;		
	}
	
	
	@Override
	public boolean addToPath(EventNode<T> node, String... sourceNodeIds) throws NodeNotFoundException { 
		
		// Add node and paths
		if(sourceNodeIds != null) {
			for(String sourceId: sourceNodeIds) {
				EventNode<T> source = this.nodeIdMap.get(sourceId);
				
				if(source == null) {
					throw new NodeNotFoundException(sourceId);
				}
				
				if(!source.addNext(node)) {
					LOGGER.warning("Skipped path from source [" + sourceId + "] to target [" + node.getId() + "]. Already exists");
					
				}
			}	
		}
		// Add node into datastructure
		return this.add(node);
	}
	
	@Override
	public PathBuilder newPath() throws NodeNotFoundException {
		this.pathBuilder.reset();
		return this.pathBuilder;
	}
	
	private void createPath(String targetNodeId, String... sourceNodeIds) throws NodeNotFoundException {
		
		EventNode<T> node = (EventNode<T>)this.nodeIdMap.get(targetNodeId);
		
		if(node != null) {
			
			if(sourceNodeIds != null) {
				for(String sourceId: sourceNodeIds) {
					EventNode<T> source = (EventNode<T>)this.nodeIdMap.get(sourceId);
					
					if(source == null) {
						throw new NodeNotFoundException(sourceId);
					}
					
					if(!source.addNext(node)) {			
						LOGGER.warning("Skipped path from source [" + sourceId + "] to target [" + targetNodeId + "]. Already exists");
					}
				}	
			}
		}
	}
	
	@Override
	public void clear() {
		this.root = null;
		this.nodeIdMap.clear();
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean contains(Object o) {
		if(!(o instanceof EventNode)) {
			throw new ClassCastException("Object must be a class of Node");
		}
		EventNode<T> node = (EventNode<T>)o;
		return this.nodeIdMap.containsKey(node.getId());
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Object o) {
		
		Identifiable idnt = (Identifiable)o;
		return this.nodeIdMap.remove(idnt.getId()) != null;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		
		int oldSize = this.size();		
		for(Object obj: c) {
			this.remove(obj);
		}		
		return oldSize == this.size() - c.size();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		
		return this.nodeIdMap.size();
	}

	@Override
	public boolean isEmpty() {
		
		return this.nodeIdMap.isEmpty();
	}

	@Override
	public boolean add(EventNode<T> e) {
		
		if(e == null || !e.isValid()) {
			throw new IllegalArgumentException("Valid node expected. Got " + e);
		}
		
		if(this.nodeIdMap.containsKey(e.getId())) {
			throw new DuplicateNodeException(e.getId());
		}
		
		return this.nodeIdMap.put(e.getId(), e) == null;
	}

	@Override
	public boolean addAll(Collection<? extends EventNode<T>> c) {
		
		int currentSize = this.size();
		for(EventNode<T> n: c) {
			this.add(n);
		}		
		return this.nodeIdMap.size() == (c.size() + currentSize);
	}

	@Override
	public Iterator<EventNode<T>> iterator() {
		
		return this.nodeIdMap.values().iterator();
	}

	@Override
	public Object[] toArray() {
		
		return this.nodeIdMap.values().toArray();
	}

	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(T[] a) {
		
		return this.nodeIdMap.values().toArray(a);
	}

	@Override
	public int depth() {
		
		return getDepth(this.root);
	}
	
	/**
	 * Recursively traverse the tree to find the node with more depth
	 * 
	 * @param n
	 * @return
	 */
	private int getDepth(EventNode<T> n) {
		
		if(n.getDepth() == 0) {
			return 1;
		}
		
		int max = 0;
		for(EventNode<T> next: n.getNextNodes()) {
			int localDepth = 1 + getDepth(next);
			if(localDepth > max) {
				max = localDepth;
			}
		}
		return max;
	}
	
	@Override
	public String toString() {
		
		return "FeedForwardEventTreeImpl [depth=" + this.depth() + ", elements=" + this.toArray() + "]";
	}
	
	
	@Override
	public int generateEventOn(EventNode<T> node, Object context)
			throws NodeNotFoundException {
	
		return this.generateEventOn(node.getId(), context);
	}
	
	@Override
	public Trace generateTracedEventOn(EventNode<T> node, Object context)
			throws NodeNotFoundException {
		
		return this.generateTracedEventOn(node.getId(), context);
	}
	
	@Override
	public Trace generateTracedEventOn(String nodeId, Object context) throws NodeNotFoundException {
		
		Trace trace = new Trace();
		EventNode<T> source = this.fetchNode(nodeId);		
		
		// as nodes get notified, it will add them to the trace
		this.notifyAllObservers(source, context, trace);

		return trace;
	}
	
	@Override
	public int generateEventOn(String nodeId, Object context) throws NodeNotFoundException {
		
		EventNode<T> source = this.fetchNode(nodeId);		
		
		return notifyAllObservers(source, context);	
	};
	
	private int notifyAllObservers(EventNode<T> n, final Object obj) {
				
		n.notifyObservers(obj);
		if(n.getDepth() == 0) {			
			return 1;
		}		
		int notified = 1;
		for(EventNode<T> next: n.getNextNodes()) {
			notified += notifyAllObservers(next, obj);
		}
		return notified;
	}
	
	
	private void notifyAllObservers(EventNode<T> n, final Object obj, Trace trace) {
		
		n.notifyObservers(obj);
		trace.addPath(n.getId());
		if(n.getDepth() == 0) {						
			return;
		}		
		for(EventNode<T> next: n.getNextNodes()) {
			this.notifyAllObservers(next, obj, trace);
		}
	}
	
	private EventNode<T> fetchNode(String id) throws NodeNotFoundException {
		
		EventNode<T> n = this.nodeIdMap.get(id);		
		if(n == null) {
			throw new NodeNotFoundException(id);
		}
		return n;
	}

	Map<String, EventNode<T>> getNodeIdMap() {
		return nodeIdMap;
	}	
	
	
	/**
	 * Private inner class used to build new paths into the tree
	 */
	private class PathBuilderImpl implements PathBuilder {
		
		private String[] from;
		private String to;
		
		@Override
		public PathBuilder from(String... from) {
			this.from = from;
			return this;
		}
		
		@Override
		public PathBuilder to(String to) {
			this.to = to;
			return this;
		}
		
		void reset() {
			this.from = null;
			this.to = null;
		}
		
		@Override
		public void add() throws NodeNotFoundException {
			FeedForwardEventTreeImpl.this.createPath(to, from);
		}
	}
}
