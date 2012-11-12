package org.cocoa4android.ui;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class UIImage {
	private Drawable drawable;
	
	private int resId;
	
	
	public UIImage(int resId){
		this.setResId(resId);
	}
	public UIImage(Drawable drawable){
		this.setDrawable(drawable);
	}

	public int getResId() {
		return resId;
	}


	public void setResId(int resId) {
		this.resId = resId;
	}
	public Drawable getDrawable() {
		return drawable;
	}
	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
	}
		

	
}
