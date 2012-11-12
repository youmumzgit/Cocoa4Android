package org.cocoa4android.ui;

import org.cocoa4android.cg.CGPoint;

public class UITouch{
	private float x;
	private float y;
	private float previousX;
	private float previousY;
	private UIView view;
	
	public UITouch(float x,float y,float previousX,float previousY,UIView view){
		this.x = x;
		this.y = y;
		this.previousX = previousX;
		this.previousY = previousY;
		this.view = view;
	}
	//fix me
	public CGPoint locationInView(UIView view){
		return new CGPoint(x,y);
	}
	public CGPoint previousLocationInView(UIView view){
		if(previousY!=0){
			return new CGPoint(previousX,previousY);
		}else{
			return new CGPoint(x,y);
		}
	}
	public UIView getView() {
		return view;
	}
}
