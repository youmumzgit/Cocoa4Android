package org.cocoa4android.ui;

import org.cocoa4android.cg.CGRect;
import org.cocoa4android.ns.NSTextAlignment;

import android.view.Gravity;
import android.widget.TextView;


public class UILabel extends UIView {
	private TextView label = null;

	public UILabel(){
		TextView lbl = new TextView(context);
		this.setLabel(lbl);
		this.setView(lbl);
		this.setTextColor(UIColor.getBlackColor());
		this.setFontSize(16);
	}
	public UILabel(CGRect frame){
		this();
		this.setFrame(frame);
	}
	public TextView getLabel() {
		return label;
	}
	public void setTextAlignment(NSTextAlignment alignment){
		switch (alignment) {
		case NSTextAlignmentLeft:
			this.label.setGravity(Gravity.LEFT);
			break;
		case NSTextAlignmentCenter:
			this.label.setGravity(Gravity.CENTER);
			break;
		case NSTextAlignmentRight:
			this.label.setGravity(Gravity.RIGHT);
			break;
		}
		
	}
	public void setLabel(TextView label) {
		this.label = label;
	}
	public void setText(String text){
		this.label.setText(text);
	}
	public String getText(){
		return (String) this.label.getText();
	}
	public void setTextColor(UIColor color){
		this.label.setTextColor(color.getColor());
	}
	public void setFontSize(int size){
		this.label.setTextSize(size);
	}
	public void setNumberOfLines(int numberOfLines){
		this.label.setLines(numberOfLines);
	}
	public int getNumberOfLines(){
		return this.label.getLineCount();
	}
}
