package org.cocoa4android.ns;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;
import android.os.Message;



public class NSTimer extends NSObject {
	double timeInterval;
	Object target;
	String selector;
	boolean repeats;
	NSInvocation invocation;
	Timer timer = new Timer();
	Handler handler;
	
	public NSTimer(double timeInterval,Object target,String selector,Object userInfo,boolean repeats){
		this.timeInterval = timeInterval;
		this.target = target;
		this.selector = selector;
		this.userInfo = userInfo;
		this.repeats = repeats;
		NSMethodSignature sig = class2NSClass(target.getClass()).instanceMethodSignatureForSelector(selector);
		
		invocation = NSInvocation.invocationWithMethodSignature(sig);
		invocation.setTarget(target);
	}
	public void fire(){
		if (handler!=null) {
			Message message = new Message();  
	        message.what = 1;  
	        handler.sendMessage(message);  
		}
	}
	private NSDate fireDate = null;
	public NSDate fireDate() {
		return fireDate;
	}
	public void setFireDate(NSDate fireDate) {
		this.fireDate = fireDate;
	}
	public void invalidate(){
		timer.cancel();
		isValid = NO;
	}
	private boolean isValid = YES;
	public boolean isValid(){
		return isValid;
	}
	public static NSTimer scheduledTimerWithTimeInterval(double timeInterval,Object target,String selector,Object userInfo,boolean repeats)
	{
		NSTimer timer = new NSTimer(timeInterval,target,selector,userInfo,repeats);
		NSRunLoop.currentRunLoop().addTimer(timer, NSDefaultRunLoopMode);
		return timer;
	}
	
	
	
	private Object userInfo;

	public Object userInfo() {
		return userInfo;
	}
	void startTimer(){
		long timeInterval =  (long) (this.timeInterval*1000);
		if (this.repeats) {
			if (fireDate==null) {
				this.timer.schedule(new TimerTask() {
					
					@Override
					public void run() {
						Message message = new Message();  
			            message.what = 1;  
			            handler.sendMessage(message);  
					}
				},timeInterval, timeInterval);  
			}else{
				this.timer.schedule(new TimerTask() {
					
					@Override
					public void run() {
						Message message = new Message();  
			            message.what = 1;  
			            handler.sendMessage(message);  
					}
				},fireDate.getDate(), timeInterval);  
			}
		}else{
			if (fireDate==null) {
				this.timer.schedule(new TimerTask() {
					
					@Override
					public void run() {
						Message message = new Message();  
			            message.what = 1;  
			            handler.sendMessage(message);  
					}
				}, timeInterval);
			}else{
				this.timer.schedule(new TimerTask() {
					
					@Override
					public void run() {
						Message message = new Message();  
			            message.what = 1;  
			            handler.sendMessage(message);  
					}
				},fireDate.getDate());
			}
		}
	}
}
