package org.cocoa4android.ns;

import org.cocoa4android.ui.UIApplication;

public class NSBundle extends NSObject {
	private static NSBundle mainBundle = null;
	private String resourcePath;
	public static NSBundle mainBundle(){
		if (mainBundle==null) {
			mainBundle = new NSBundle();
			mainBundle.resourcePath = UIApplication.sharedApplication().getActivity().getApplicationContext().getFilesDir().getAbsolutePath();
		}
		return mainBundle;
	}
	public String resourcePath(){
		return resourcePath;
	}
}
