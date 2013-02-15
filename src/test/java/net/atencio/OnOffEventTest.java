package net.atencio;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import net.atencio.util.event.tree.FeedForwardEventTree;
import net.atencio.util.event.tree.FeedForwardEventTreeImpl;
import net.atencio.util.event.tree.Node;
import net.atencio.util.event.tree.NodeNotFoundException;
import net.atencio.util.event.tree.RootNode;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OnOffEventTest {

	private FeedForwardEventTree<Boolean> basicTree;
	private RootNode<Boolean> root;
	
	@Before
	public void setUp() {
		this.basicTree = new FeedForwardEventTreeImpl<Boolean>();
		this.root = new RootNode<Boolean>(Boolean.FALSE);
		this.basicTree.setRoot(this.root);
		Assert.assertFalse(this.basicTree.isEmpty());
	}
		
	@After
	public void tearDown() {
		this.basicTree.clear();
		Assert.assertEquals(this.basicTree.size(), 0);
	}
	
	@Test
	public void onOffTest() throws NodeNotFoundException {

		// Tree starts all off
		Node<Boolean> one = new Node<Boolean>("1", Boolean.FALSE);
		Node<Boolean> two = new Node<Boolean>("2", Boolean.FALSE);
		Node<Boolean> three = new Node<Boolean>("3", Boolean.FALSE);
		
		List<Node<Boolean>> nodes = new ArrayList<Node<Boolean>>();
		nodes.add(one);
		nodes.add(two);
		nodes.add(three);
		
		for(Node<Boolean> n: nodes) {
			n.addObserver(new RootTurnOnNextNodeObserver(this.root));
		}
				
		// Add three nodes
		this.basicTree.add(one);
		this.basicTree.add(two);
		this.basicTree.add(three);
		
		// Add paths
		this.basicTree.addPath("1", "root");		
		this.basicTree.addPath("2", "1");		
		this.basicTree.addPath("3", "1", "2");
		
		// Turn all nodes on by turning root on and propagating
		this.root.updateValue(Boolean.TRUE);
		
		int notified = this.basicTree.generateEventOn("root", Boolean.TRUE);
		Assert.assertEquals(notified, 5);
		
		Assert.assertTrue(this.root.getValue());
		for(Node<Boolean> n: nodes) {
			Assert.assertTrue(n.getValue());
		}
	}
	
	/**
	 * Observer on root node. If the root node turns on, all of will turn on
	 * 
	 * @author luijar
	 *
	 */
	private class RootTurnOnNextNodeObserver implements Observer {
		
		private Node<Boolean> root;
		
		public RootTurnOnNextNodeObserver(Node<Boolean> root) {
			this.root = root;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public void update(Observable o, Object arg) {			
			Node<Boolean> thisNode = (Node<Boolean>)o;
			thisNode.updateValue(root.getValue());			
		}		
	}
}
