package org.cocoa4android.ns;

public class NSTimer extends NSObject {
	private double timeInterval;
	private Object target;
	private String selector;
	private boolean repeats;
	
	public NSTimer(double timeInterval,Object target,String selector,Object userInfo,boolean repeats){
		this.timeInterval = timeInterval;
		this.target = target;
		this.selector = selector;
		this.userInfo = userInfo;
		this.repeats = repeats;
	}
	public static NSTimer scheduledTimerWithTimeInterval(double timeInterval,Object target,String selector,Object userInfo,boolean repeats)
	{
		NSTimer timer = new NSTimer(timeInterval,target,selector,userInfo,repeats);
		//TODO start timer
		return timer;
	}
	
	
	
	private Object userInfo;

	public Object userInfo() {
		return userInfo;
	}
}
