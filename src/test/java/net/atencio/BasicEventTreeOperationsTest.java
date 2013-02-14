package net.atencio;

import java.awt.Color;

import net.atencio.util.event.tree.FeedForwardEventTree;
import net.atencio.util.event.tree.FeedForwardEventTreeImpl;
import net.atencio.util.event.tree.NodeNotFoundException;
import net.atencio.util.event.tree.model.NodeImpl;
import net.atencio.util.event.tree.model.RootNode;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BasicEventTreeOperationsTest {

	FeedForwardEventTree<Color> basicTree;
	
	@Before
	public void setUp() {
		this.basicTree = new FeedForwardEventTreeImpl<Color>();
	}
	
	@Test
	public void testAddNode() throws NodeNotFoundException {
		
		this.basicTree.setRoot(new RootNode<Color>(Color.BLUE));
		this.basicTree.add(new NodeImpl<Color>("1", Color.BLACK));
		this.basicTree.add(new NodeImpl<Color>("2", Color.RED));
		this.basicTree.add(new NodeImpl<Color>("3", Color.CYAN));
		
		Assert.assertFalse(this.basicTree.isEmpty());
		Assert.assertEquals(this.basicTree.getDepth(), 1);
		
		this.basicTree.addPath("1", "root");
		Assert.assertEquals(this.basicTree.getDepth(), 2);
	}
}
