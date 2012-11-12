package org.cocoa4android.ui;

import org.cocoa4android.cg.CGRect;

public class UIWindow extends UIView {
	public UIWindow(CGRect frame){
		super(frame);
		this.setBackgroundColor(UIColor.getWhiteColor());
	}
	public UIWindow(){
		super();
		this.setBackgroundColor(UIColor.getWhiteColor());
	}
	public UIWindow(int viewid) {
		super(viewid);
		this.setBackgroundColor(UIColor.getWhiteColor());
	}
	private UIViewController rootViewController;
	public void makeKeyAndVisible(){
		this.setHidden(false);
	}
	public UIViewController getRootViewController() {
		return rootViewController;
	}
	public void setRootViewController(UIViewController rootViewController) {
		this.rootViewController = rootViewController;
		this.addSubView(rootViewController.getView());
	}
}
