package org.cocoa4android.ui;

import org.cocoa4android.R;
import org.cocoa4android.cg.CGRect;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;

public class UITabBar extends UIView {
	private float tabBarHeight;
	public UITabBar() {
		CGRect appFrame = UIScreen.mainScreen().applicationFrame();
		tabBarHeight = appFrame.size.height/10.0f;
		this.setFrame(CGRectMake(0,appFrame.size().height()-tabBarHeight,appFrame.size().width(),tabBarHeight));
		Bitmap bitmap = BitmapFactory.decodeResource(UIApplication.sharedApplication().getActivity().getResources(), R.drawable.zz_c4a_tabbar_background);  
		BitmapDrawable bd = new BitmapDrawable(bitmap);  
		bd.setTileModeX(TileMode.REPEAT);
		bd.setDither(true);  
		this.getView().setBackgroundDrawable(bd); 
	}
}
