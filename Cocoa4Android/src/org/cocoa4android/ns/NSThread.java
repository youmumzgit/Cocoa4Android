package org.cocoa4android.ns;

public class NSThread extends NSObject {
	private Thread thread;
	public NSThread(Thread thread){
		this.thread = thread;
	}
}
