package org.cocoa4android.navigationsample;

import org.cocoa4android.cg.CGSize;
import org.cocoa4android.ui.UIAppDelegate;
import org.cocoa4android.ui.UINavigationController;
import org.cocoa4android.ui.UIScreen;

public class NavigationControllerAppDelegate extends UIAppDelegate {
	@Override
	public void applicationDidFinishLaunching() {
		//this is according to the size of your designed app
		//you can change the value to any value 
		//Cocoa4Android will resize the app to fit other screen
		UIScreen.mainScreen().setStandardScreenSize(new CGSize(320, 480));
		
		
		FirstViewController firstViewController = new FirstViewController();
		UINavigationController navigationController = new UINavigationController();
		navigationController.pushViewController(firstViewController, NO);
		this.window.setRootViewController(navigationController);
	}

}
