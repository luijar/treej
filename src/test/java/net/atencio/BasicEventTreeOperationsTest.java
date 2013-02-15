package net.atencio;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import net.atencio.util.ChangeableBoolean;
import net.atencio.util.event.tree.FeedForwardEventTree;
import net.atencio.util.event.tree.FeedForwardEventTreeImpl;
import net.atencio.util.event.tree.Node;
import net.atencio.util.event.tree.NodeNotFoundException;
import net.atencio.util.event.tree.RootNode;

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
		
		Node<Color> one = new Node<Color>("1", Color.BLACK);
		Node<Color> two = new Node<Color>("2", Color.RED);
		Node<Color> three = new Node<Color>("3", Color.CYAN);
		
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
	}
	
	@Test
	public void testGenerateEventOn() throws NodeNotFoundException {
		
		Node<Color> one = new Node<Color>("1", Color.BLACK);
		Node<Color> two = new Node<Color>("2", Color.RED);
		Node<Color> three = new Node<Color>("3", Color.CYAN);
		
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
}
