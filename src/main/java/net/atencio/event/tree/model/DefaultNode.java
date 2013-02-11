package main.java.net.atencio.event.tree.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DefaultNode implements Node {

	private String id;
	private Node previous;
	private List<Node> next;
	
	DefaultNode() {
		this.next = new ArrayList<Node>();
	}
	
	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public Node getPrevious() {
		return this.previous;
	}

	@Override
	public List<Node> getNext() {
		return Collections.unmodifiableList(this.next);
	}
	
	@Override
	public boolean isRoot() {
		return this.previous == null;
	}
	
	public static class Builder {
		private DefaultNode _t = new DefaultNode();
		
		public Builder id(final String id) {
			_t.id = id;
			return this;
		}
		
		public Builder previous(final Node parent) {
			_t.previous = parent;
			return this;
		}
		
		public Builder addNode(final Node n) {
			_t.next.add(n);
			return this;
		}
		
		public Builder addNodes(List<Node> next) {
			_t.next = next;
			return this;
		}
		
		public DefaultNode build() {
			if(_t.id == null || _t.id.isEmpty()) {
				throw new IllegalArgumentException("Node needs a valid Id");
			}
			return _t;
		}
	}
}
