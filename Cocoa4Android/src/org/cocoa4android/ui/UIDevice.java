package org.cocoa4android.ui;

import org.cocoa4android.ns.NSObject;

public class UIDevice extends NSObject {
	private static UIDevice currentDevice;
	public static UIDevice currentDevice(){
		if (currentDevice==null) {
			currentDevice = new UIDevice();
		}
		return currentDevice;
	}
	public boolean isMultitaskingSupported(){
		return YES;
	}
}
