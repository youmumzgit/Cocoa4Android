package org.cocoa4android.ui;

import org.cocoa4android.cg.CGRect;

import android.widget.ToggleButton;

public class UISwitcher extends UIControl {
	protected ToggleButton switcher = null;
	public ToggleButton getSwitcher() {
		return switcher;
	}
	public void setSwitcher(ToggleButton switcher) {
		this.switcher = switcher;
	}
	public UISwitcher(){
		ToggleButton btn = new ToggleButton(context);
		this.setSwitcher(btn);
		btn.setFocusable(NO);
		this.setView(btn);
	}
	public UISwitcher(CGRect frame){
		this();
		this.setFrame(frame);
	}
}
