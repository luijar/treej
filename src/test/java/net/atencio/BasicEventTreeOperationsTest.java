package net.atencio;

import java.awt.Color;

import net.atencio.event.tree.FeedForwardEventTree;
import net.atencio.event.tree.FeedForwardEventTreeImpl;
import net.atencio.event.tree.model.Node;

import org.junit.Before;
import org.junit.Test;

public class BasicEventTreeOperationsTest {

	FeedForwardEventTree basicTree;
	
	@Before
	public void setUp() {
		this.basicTree = new FeedForwardEventTreeImpl();
	}
	
	@Test
	public void testAddNode() {
		
		Node<Color> n = new Node.Builder<Color>()
		.id("123")
		.value(Color.BLUE)
		.build();
		
		basicTree.createRootNode(null).addNode(null, "").
	}
}
