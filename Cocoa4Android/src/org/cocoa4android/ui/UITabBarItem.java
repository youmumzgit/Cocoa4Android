package org.cocoa4android.ui;

public class UITabBarItem extends UIBarItem {
	private boolean enalbed = YES;
	public boolean isEnalbed() {
		return enalbed;
	}
	public void setEnalbed(boolean enalbed) {
		this.enalbed = enalbed;
	}
	private String title = null;
	public String title() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	private UIImage image = null;
	public UIImage image() {
		return image;
	}
	public void setImage(UIImage image) {
		this.image = image;
	}
	// default is 0
	private int tag = 0;
	public int tag() {
		return tag;
	}
	public void setTag(int tag) {
		this.tag = tag;
	}
	
}
