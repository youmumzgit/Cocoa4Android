package org.cocoa4android.helloworld;

import org.cocoa4android.ns.NSTextAlignment;
import org.cocoa4android.ui.UIColor;
import org.cocoa4android.ui.UILabel;
import org.cocoa4android.ui.UIViewController;

public class HelloWorldViewController extends UIViewController {
	@Override
	protected void viewDidLoad() {
		super.viewDidLoad();
		this.view.setBackgroundColor(UIColor.lightGrayColor());
		UILabel label = new UILabel(CGRectMake(0, 0, 320, 40));
		label.setTextAlignment(NSTextAlignment.NSTextAlignmentCenter);
		label.setFontSize(20);
		label.setText("Hello World");
		label.setCenter(CGPointMake(160, 200));
		this.view.addSubview(label);
	}
}
