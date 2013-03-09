package net.atencio.util.event.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Registers a list of ids that represent trace of EventNode ids that was taken from an event
 * generated from a source event node.
 * 
 * @author luijar
 *
 */
public class Trace {

	private List<String> trace;
	
	Trace() {
		this.trace = new ArrayList<String>();
	}
	
	void addPath(String path) {
		
		this.trace.add(path);
	}
	
	public List<String> getTrace() {
		
		return Collections.unmodifiableList(this.trace);
	}

	public int getCount() {
		
		return this.trace.size();
	}
	
	@Override
	public String toString() {
		return "Trace [trace=" + trace + "]";
	}
	
	
}
