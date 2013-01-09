package org.cocoa4android.ns;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class NSRunLoop extends NSObject {
	
	
	private Looper looper;
	
	
	public static NSRunLoop currentRunLoop() {
		Looper looper = Looper.myLooper();
		if (looper==null) {
			//FIXME create new looper and attach it
		}
		return new NSRunLoop(looper);
	}
	public static NSRunLoop mainRunLoop(){
		return new NSRunLoop(Looper.getMainLooper());
	}
	NSRunLoop(Looper looper){
		this.looper = looper;
	}
	private String currentMode = NSDefaultRunLoopMode;
	public String currentMode(){
		return currentMode;
	}
	//FIXME didn't use the mode
	public void addTimer(NSTimer aTimer,String mode){
		final Handler handler = new CocoaHandler(looper,aTimer);
		aTimer.handler = handler;
        aTimer.startTimer();
	}
	static class CocoaHandler extends Handler{
		private NSTimer timer;
		public CocoaHandler(Looper looper,NSTimer timer) {
			super(looper);
			this.timer = timer;
		}

		public void handleMessage(Message msg) { 
			if (msg.what==1) {
				timer.invocation.invoke();
				if (!timer.repeats) {
					timer.invalidate();
				}
			}
		}
	}
}
