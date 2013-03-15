package net.atencio;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import net.atencio.util.event.tree.FeedForwardEventTree;
import net.atencio.util.event.tree.FeedForwardEventTreeImpl;
import net.atencio.util.event.tree.EventNode;
import net.atencio.util.event.tree.NodeNotFoundException;
import net.atencio.util.event.tree.RootNode;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for simple on/off events
 * 
 * @author luijar
 *
 */
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
		EventNode<Boolean> one = new EventNode<Boolean>("1", Boolean.FALSE);
		EventNode<Boolean> two = new EventNode<Boolean>("2", Boolean.FALSE);
		EventNode<Boolean> three = new EventNode<Boolean>("3", Boolean.FALSE);
		
		List<EventNode<Boolean>> nodes = new ArrayList<EventNode<Boolean>>();
		nodes.add(one);
		nodes.add(two);
		nodes.add(three);
		
		for(EventNode<Boolean> n: nodes) {
			n.addObserver(new RootTurnOnNextNodeObserver(this.root));
		}
				
		// Add three nodes
		this.basicTree.add(one);
		this.basicTree.add(two);
		this.basicTree.add(three);
		
		// Add paths
		this.basicTree.newPath().from("root").to("1").add();		
		this.basicTree.newPath().from("1").to("2").add();		
		this.basicTree.newPath().from("1", "2").to("3").add();
		
		// Turn all nodes on by turning root on and propagating
		this.root.updateValue(Boolean.TRUE);
		
		int notified = this.basicTree.generateEventOn("root", Boolean.TRUE);
		Assert.assertEquals(notified, 5);
		
		Assert.assertTrue(this.root.getValue());
		for(EventNode<Boolean> n: nodes) {
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
		
		private EventNode<Boolean> root;
		
		public RootTurnOnNextNodeObserver(EventNode<Boolean> root) {
			this.root = root;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public void update(Observable o, Object arg) {			
			EventNode<Boolean> thisNode = (EventNode<Boolean>)o;
			thisNode.updateValue(root.getValue());			
		}		
	}
}
