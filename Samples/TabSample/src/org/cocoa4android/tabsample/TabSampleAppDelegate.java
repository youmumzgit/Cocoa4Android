package org.cocoa4android.tabsample;

import org.cocoa4android.cg.CGSize;
import org.cocoa4android.ns.NSArray;
import org.cocoa4android.ns.NSMutableArray;
import org.cocoa4android.ui.UIAppDelegate;
import org.cocoa4android.ui.UIScreen;
import org.cocoa4android.ui.UITabBarController;

public class TabSampleAppDelegate extends UIAppDelegate {

	@Override
	public void applicationDidFinishLaunching() {
		//this is according to the size of your designed app
		//you can change the value to any value 
		//Cocoa4Android will resize the app to fit other screen
		UIScreen.mainScreen().setStandardScreenSize(new CGSize(320, 480));
		
		UITabBarController tabBarController = new UITabBarController();
		
		
		
		NSMutableArray viewControllers = NSMutableArray.arrayWithObjects(new FirstViewController(),new SecondViewController(),new ThirdViewController());
		tabBarController.setViewControllers(viewControllers);
		this.window.setRootViewController(tabBarController);
	}

}
