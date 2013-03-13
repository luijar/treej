package net.atencio;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import net.atencio.util.event.tree.EventNode;
import net.atencio.util.event.tree.FeedForwardEventTree;
import net.atencio.util.event.tree.FeedForwardEventTreeImpl;
import net.atencio.util.event.tree.NodeNotFoundException;
import net.atencio.util.event.tree.RootNode;
import net.atencio.util.event.tree.Trace;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BasicEventTreeOperationsTest {

	private FeedForwardEventTree<Color> basicTree;
	private RootNode<Color> root;
	
	@Before
	public void setUp() {
		this.basicTree = new FeedForwardEventTreeImpl<Color>();
		this.root = new RootNode<Color>(Color.BLUE);
		this.basicTree.setRoot(this.root);
		Assert.assertFalse(this.basicTree.isEmpty());
	}
	
	
	@After
	public void tearDown() {
		this.basicTree.clear();
		Assert.assertEquals(this.basicTree.size(), 0);
	}
	
	@Test
	public void testAddNode() throws NodeNotFoundException {
		
		EventNode<Color> one = new EventNode<Color>("1", Color.BLACK);
		EventNode<Color> two = new EventNode<Color>("2", Color.RED);
		EventNode<Color> three = new EventNode<Color>("3", Color.CYAN);
		
		// Add three nodes
		this.basicTree.add(one);
		this.basicTree.add(two);
		this.basicTree.add(three);
		
		Assert.assertFalse(this.basicTree.isEmpty());
		Assert.assertEquals(this.basicTree.depth(), 1);
				
		this.basicTree.addPath("1", "root");
		Assert.assertEquals(this.basicTree.depth(), 2);
		
		// This path should be omitted, depth remains the same
		this.basicTree.addPath("1", "root");
		Assert.assertEquals(this.basicTree.depth(), 2);
		
		this.basicTree.addPath("2", "1");
		Assert.assertEquals(this.basicTree.depth(), 3);
		
		this.basicTree.addPath("3", "2");
		Assert.assertEquals(this.basicTree.depth(), 4);
		
		Assert.assertEquals(this.basicTree.size(), 4);
		
		int notified = this.basicTree.generateEventOn("root", Color.YELLOW, true);
		Assert.assertEquals(notified, 4);
		
		List<EventNode<Color>> toRemove = new ArrayList<EventNode<Color>>();
		toRemove.add(one);
		toRemove.add(two);
		Assert.assertEquals(4, this.basicTree.size());
		this.basicTree.removeAll(toRemove);
		Assert.assertEquals(2, this.basicTree.size());
	}
	
	@Test
	public void testGenerateEventOn() throws NodeNotFoundException {
		
		EventNode<Color> one = new EventNode<Color>("1", Color.BLACK);
		EventNode<Color> two = new EventNode<Color>("2", Color.RED);
		EventNode<Color> three = new EventNode<Color>("3", Color.CYAN);
		
		// Add three nodes
		this.basicTree.add(one);
		this.basicTree.add(two);
		this.basicTree.add(three);
		
		// Add paths
		this.basicTree.addPath("1", "root");		
		this.basicTree.addPath("2", "1");		
		this.basicTree.addPath("3", "2");
		
		// Add Observers to node 1
		final ChangeableBoolean isNotified = new ChangeableBoolean(false);
		one.addObserver(new Observer() {
			@Override
			public void update(Observable o, Object arg) {
				isNotified.set(true);
			}
		});
		one.markAsChanged();
		
		int notified = this.basicTree.generateEventOn("root", Color.YELLOW, true);
		Assert.assertEquals(notified, 4);
		
		Assert.assertTrue(isNotified.get());
	}
	
	@Test
	public void testAddAll() {
		
		EventNode<Color> one = new EventNode<Color>("1", Color.BLACK);
		EventNode<Color> two = new EventNode<Color>("2", Color.RED);
		EventNode<Color> three = new EventNode<Color>("3", Color.CYAN);
		
		List<EventNode<Color>> items = new ArrayList<EventNode<Color>>();
		items.add(one); items.add(two); items.add(three);
		
		this.basicTree.addAll(items);
		Assert.assertEquals(this.basicTree.size(), 3 + 1);
	}
	
	@Test
	public void testGenerateTracedEventOnNodeId() throws NodeNotFoundException {
		
		EventNode<Color> one = new EventNode<Color>("1", Color.BLACK);
		EventNode<Color> two = new EventNode<Color>("2", Color.RED);
		EventNode<Color> three = new EventNode<Color>("3", Color.CYAN);
		
		// Add three nodes
		this.basicTree.add(one);
		this.basicTree.add(two);
		this.basicTree.add(three);
		
		// Add paths
		this.basicTree.addPath("1", "root");		
		this.basicTree.addPath("2", "1");		
		this.basicTree.addPath("3", "2");
		
		// Add Observers to node 1
		final ChangeableBoolean isNotified = new ChangeableBoolean(false);
		
		// Add an observer to the node
		one.addObserver(new Observer() {
			@Override
			public void update(Observable o, Object arg) {
				isNotified.set(true);
			}
		});
		
		// trigger the change on a node
		one.markAsChanged();
		
		Trace trace = this.basicTree.generateTracedEventOn("root", Color.YELLOW, true);
		Assert.assertEquals(trace.getCount(), 4);		
		Assert.assertTrue(isNotified.get());		
	}
	
	private static class ChangeableBoolean implements Serializable {

		private static final long serialVersionUID = 1L;
		private boolean val;
		
		public ChangeableBoolean(boolean initialVal) {
			this.val = initialVal;
		}
		
		public synchronized boolean get() {
			return val;
		}
		
		public synchronized void set(boolean newVal) {
			val = newVal;
		}

		@Override
		public String toString() {
			return "ChangeableBoolean [val=" + val + "]";
		}	
	}
}
