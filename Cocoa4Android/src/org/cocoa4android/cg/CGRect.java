package org.cocoa4android.cg;

import org.cocoa4android.ui.UIScreen;

import android.graphics.Rect;

public class CGRect {
	private CGPoint origin;
	private CGSize size;
	
	public CGRect(float x,float y,float width,float height){
		this.origin = new CGPoint(x,y);
		this.size = new CGSize(width,height);
	}
	public CGRect(Rect r){
		float density = UIScreen.getMainScreen().getDensity();
		this.origin = new CGPoint(r.left/density,r.top/density);
		this.size = new CGSize((r.right-r.left)/density,(r.bottom - r.top)/density);
	}
	public CGPoint getOrigin() {
		return origin;
	}
	public void setOrigin(CGPoint origin) {
		this.origin = origin;
	}
	public CGSize getSize() {
		return size;
	}
	public void setSize(CGSize size) {
		this.size = size;
	}
	public Rect getRect(){
		float density = UIScreen.getMainScreen().getDensity();
		return new Rect((int)(this.origin.getX()*density),(int)(this.origin.getY()*density),(int)((this.origin.getX()+this.size.getWidth())*density),(int)((this.origin.getY()+this.size.getHeight())*density));
	}
}
