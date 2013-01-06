package org.cocoa4android.tabsample;

import org.cocoa4android.ns.NSTextAlignment;
import org.cocoa4android.ui.UIImage;
import org.cocoa4android.ui.UILabel;
import org.cocoa4android.ui.UITabBarItem;
import org.cocoa4android.ui.UIViewController;

public class ThirdViewController extends UIViewController {
	public ThirdViewController() {
		this.setTabBarItem(new UITabBarItem("Third",UIImage.imageNamed(R.drawable.third),0));
	}
	protected void viewDidLoad() {
		super.viewDidLoad();
		UILabel secondLabel = new UILabel();
		secondLabel.setText("ThirdView");
		secondLabel.setFontSize(18);
		secondLabel.setFrame(CGRectMake(0, 0, 320, 200));
		secondLabel.setTextAlignment(NSTextAlignment.NSTextAlignmentCenter);
		this.view.addSubview(secondLabel);
	}
}
