package org.cocoa4android.ui;

import org.cocoa4android.cg.CGRect;


import android.graphics.Bitmap;
import android.widget.ImageView;

public class UIImageView extends UIView {
	
	protected ImageView imageView;
	private UIImage image;
	
	public UIImageView(UIImage image){
		this();
		this.setImage(image);
	}
	public UIImageView(){
		imageView = new ImageView(this.context);
		//fix me
		imageView.setScaleType(ImageView.ScaleType.FIT_XY); 
		this.setView(imageView);
	}
	public UIImageView(Bitmap bitmap){
		this();
		imageView.setImageBitmap(bitmap);
	}
	public UIImageView(CGRect frame){
		this();
		this.setFrame(frame);
	}
	public UIImage getImage() {
		return image;
	}
	public void setImage(UIImage image) {
		this.image = image;
		if(image!=null){
			imageView.setImageResource(image.getResId());
			/*
			Rect r = imageView.getDrawable().getBounds();
			this.setFrame(new CGRect(r));
			*/
		}
	}
}
