package org.cocoa4android.ns;

import java.util.TimerTask;


public class NSTimer extends NSObject {
	private double timeInterval;
	private Object target;
	private String selector;
	private boolean repeats;
	private NSInvocation invocation;
	
	private TimerTask timerTask;
	
	public NSTimer(double timeInterval,Object target,String selector,Object userInfo,boolean repeats){
		this.timeInterval = timeInterval;
		this.target = target;
		this.selector = selector;
		this.userInfo = userInfo;
		this.repeats = repeats;
		NSMethodSignature sig = class2NSClass(target.getClass()).instanceMethodSignatureForSelector(selector);
		invocation = NSInvocation.invocationWithMethodSignature(sig);
		timerTask = new TimerTask() {
			
			@Override
			public void run() {
				invocation.invoke();
			}
		};
	}
	public void fire(){
		timerTask.run();
	}
	private NSDate fireDate;
	public NSDate fireDate() {
		return fireDate;
	}
	public void setFireDate(NSDate fireDate) {
		this.fireDate = fireDate;
	}
	public void invalidate(){
		
	}
	public boolean isValid(){
		return YES;
	}
	//FIXME start timer
	public static NSTimer scheduledTimerWithTimeInterval(double timeInterval,Object target,String selector,Object userInfo,boolean repeats)
	{
		NSTimer timer = new NSTimer(timeInterval,target,selector,userInfo,repeats);
		
		return timer;
	}
	
	
	
	private Object userInfo;

	public Object userInfo() {
		return userInfo;
	}
}
