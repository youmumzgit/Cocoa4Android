package org.cocoa4android.ui;

import android.graphics.Color;

public class UIColor {
	private int color;
	public UIColor(int color){
		this.color = color;
	}
	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
	public static UIColor getBlackColor(){
		return new UIColor(Color.BLACK);
	}
	public static UIColor getWhiteColor(){
		return new UIColor(Color.WHITE);
	}
	public static UIColor getClearColor(){
		return new UIColor(Color.TRANSPARENT);
	}
	public static UIColor getGrayColor(){
		return new UIColor(Color.GRAY);
	}
	public static UIColor getDarkGrayColor(){
		return new UIColor(Color.DKGRAY);
	}
	public static UIColor getLightGrayColor(){
		return new UIColor(Color.LTGRAY);
	}
	
	public static UIColor getBlueColor(){
		return new UIColor(Color.BLUE);
	}
	public static UIColor getGreenColor(){
		return new UIColor(Color.GREEN);
	}
	public static UIColor getRedColor(){
		return new UIColor(Color.RED);
	}
	public static UIColor getYellowColor(){
		return new UIColor(Color.YELLOW);
	}
	
}
