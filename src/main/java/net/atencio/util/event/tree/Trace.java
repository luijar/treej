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
		
		StringBuilder traceBuilder = new StringBuilder();
		traceBuilder.append("Trace [ ");
		for(int i = 0; i < this.trace.size(); i++) {
			traceBuilder.append(this.trace.get(i));
			if(i != this.trace.size() - 1) {
				traceBuilder.append("->");
			}
		}
		traceBuilder.append(" ]");
		
		return traceBuilder.toString();
	}
	
	
}
