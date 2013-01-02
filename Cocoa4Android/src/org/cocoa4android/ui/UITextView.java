package org.cocoa4android.ui;

import android.widget.EditText;

public class UITextView extends UIView {
	private EditText editText = null;
	public UITextView(){
		editText = new EditText(context);
		this.setView(editText);
		this.setBackgroundColor(UIColor.clearColor());
	}
}
