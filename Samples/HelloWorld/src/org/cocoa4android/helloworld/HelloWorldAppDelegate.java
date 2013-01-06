package org.cocoa4android.helloworld;

import org.cocoa4android.cg.CGSize;
import org.cocoa4android.ui.UIAppDelegate;
import org.cocoa4android.ui.UIScreen;

public class HelloWorldAppDelegate extends  UIAppDelegate{

	@Override
	public void applicationDidFinishLaunching() {
		UIScreen.mainScreen().setStandardScreenSize(new CGSize(320, 480));
		HelloWorldViewController viewController = new HelloWorldViewController();
		this.window.setRootViewController(viewController);
	}


}
