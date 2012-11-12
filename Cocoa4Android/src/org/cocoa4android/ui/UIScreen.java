package org.cocoa4android.ui;

import org.cocoa4android.cg.CGRect;

public class UIScreen {
	private CGRect bounds;
	private CGRect applicationFrame;
	private float density;
	private static UIScreen mainScreen = null;
	public static UIScreen getMainScreen(){
		if(mainScreen ==null){
			mainScreen = new UIScreen();
		}
		return mainScreen;
	}
	public CGRect getBounds() {
		return bounds;
	}
	public void setBounds(CGRect bounds) {
		this.bounds = bounds;
	}
	public CGRect getApplicationFrame() {
		return applicationFrame;
	}
	public void setApplicationFrame(CGRect applicationFrame) {
		this.applicationFrame = applicationFrame;
	}
	public float getDensity() {
		return density;
	}
	public void setDensity(float density) {
		this.density = density;
	}
}
