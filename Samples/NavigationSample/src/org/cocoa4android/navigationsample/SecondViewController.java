package org.cocoa4android.navigationsample;


import org.cocoa4android.ui.UILabel;
import org.cocoa4android.ui.UIViewController;

public class SecondViewController extends UIViewController {
	@Override
	protected void viewDidLoad() {
		super.viewDidLoad();
		this.setTitle("SecondView");
		
		UILabel secondLabel = new UILabel();
		secondLabel.setText("SecondView");
		secondLabel.setFontSize(18);
		this.view.addSubview(secondLabel);
	}
}
