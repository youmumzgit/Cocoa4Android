package org.cocoa4android.ca;

import org.cocoa4android.cg.CGRect;
import org.cocoa4android.ns.NSObject;
import org.cocoa4android.ui.UIView;

public class CALayer extends NSObject {
	private UIView view;
	public CALayer(UIView view){
		this.view = view;
	}
	public void setUIView(UIView view){
		this.view = view;
	}
	public void setNeedsDisplay(){
		view.getView().invalidate();
	}
	public void setNeedsDisplayInRect(CGRect rect){
		view.getView().invalidate(rect.getRect());
	}
}
