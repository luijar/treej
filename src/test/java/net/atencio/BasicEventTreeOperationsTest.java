package net.atencio;

import java.awt.Color;

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

	FeedForwardEventTree<Color> basicTree;
	
	@Before
	public void setUp() {
		this.basicTree = new FeedForwardEventTreeImpl<Color>();
		this.basicTree.setRoot(new RootNode<Color>(Color.BLUE));
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
}
