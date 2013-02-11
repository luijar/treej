package main.java.net.atencio.event.tree.factory;

import java.util.logging.Logger;

import main.java.net.atencio.event.tree.FeedForwardEventTree;
import main.java.net.atencio.event.tree.FeedForwardEventTreeImpl;
import main.java.net.atencio.event.tree.RecursiveFeedForwardEventTree;

/**
 * Feed forward Event tree implementation. Event trees can be recursive or non-recursive. 
 * 
 * <h3>Recursive</h3>
 * <p>
 * 	  TBD
 * </p>
 * 
 * 
 * <h3>Non-Recursive</h3>
 * <p>
 * 	TBD
 * </p>
 * 
 * @author luijar
 *
 */
public class FeedForwardTreeFactory {

	private static final Logger LOGGER = Logger.getLogger(FeedForwardTreeFactory.class.getName());
	
	public enum Type {		
		RECURSIVE,		
		NON_RECURSIVE
	}
	
	private static FeedForwardEventTree instance;
	
	public static FeedForwardEventTree make(Type type) {
		
		if(instance == null) {
			switch(type) {
				case RECURSIVE:
					instance = new RecursiveFeedForwardEventTree();
					break;
				default: 
					instance = new FeedForwardEventTreeImpl();
			}	
			LOGGER.info("Initializing new Feed-Forward Event Tree");
		}
		return instance;
	}
}
