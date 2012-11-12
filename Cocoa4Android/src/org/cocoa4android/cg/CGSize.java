package org.cocoa4android.cg;

public class CGSize {
	private float width;
	private float height;
	public CGSize(float width,float height){
		this.setWidth(width);
		this.setHeight(height);
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
}
