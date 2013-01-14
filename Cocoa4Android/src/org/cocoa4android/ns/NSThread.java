package org.cocoa4android.ns;

import android.os.Looper;

public class NSThread extends NSObject {
	public static boolean isMainThread(){
		return Looper.myLooper() == Looper.getMainLooper();
	}
	
	private NSInvocation invocation;
	private Thread thread;
	private boolean isFinished = NO;
	public NSThread(String selector,Object target,Object data){
		NSMethodSignature signature = class2NSClass(target.getClass()).instanceMethodSignatureForSelector(selector);
		invocation = NSInvocation.invocationWithMethodSignature(signature);
		invocation.setTarget(target);
		invocation.setArgument(data, 2);
		thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				isFinished = NO;
				isCancelled = NO;
				invocation.invoke();
				isFinished = YES;
			}
		});
	}
	
	public static NSThread detachNewThreadSelector(String selector,Object target,Object data){
		NSThread t = new NSThread(selector, target, data);
		t.start();
		return t;
	}
	
	public void start(){
		thread.start();
	}
	public void cancel(){
		thread.interrupt();
		isCancelled = YES;
	}
	public boolean isExecuting(){
		return thread.isAlive();
	}
	public boolean isFinished(){
		return isFinished;
	}
	private boolean isCancelled = NO;
	public boolean isCancelled(){
		return isCancelled;
	}
	public String name() {
		return thread.getName();
	}

	public void setName(String name) {
		this.thread.setName(name);
	}
	
	
	public void setThreadPriority(double priority){
		this.thread.setPriority((int) priority);
	}
	public double threadPriority(){
		return this.thread.getPriority();
	}
}
