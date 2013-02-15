package net.atencio.util;

import java.io.Serializable;

public class ChangeableBoolean implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean val;
	
	public ChangeableBoolean() { 		
		this.val = false;
	}
	
	public ChangeableBoolean(boolean initialVal) {
		this.val = initialVal;
	}
	
	public synchronized boolean checkAndSet(boolean newVal) {
		boolean prev = val;
		val = newVal;
		return prev;
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