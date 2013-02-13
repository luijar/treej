package net.atencio;

import java.awt.Color;

import net.atencio.util.event.tree.FeedForwardEventTree;
import net.atencio.util.event.tree.FeedForwardEventTreeImpl;
import net.atencio.util.event.tree.model.NodeImpl;
import net.atencio.util.event.tree.model.RootNode;

import org.junit.Before;
import org.junit.Test;

public class BasicEventTreeOperationsTest {

	FeedForwardEventTree<Color> basicTree;
	
	@Before
	public void setUp() {
		this.basicTree = new FeedForwardEventTreeImpl<Color>();
	}
	
	@Test
	public void testAddNode() {
		
		this.basicTree.setRoot(new RootNode<Color>(Color.BLUE));
		this.basicTree.add(new NodeImpl<Color>("1", Color.BLACK));
	}
}
