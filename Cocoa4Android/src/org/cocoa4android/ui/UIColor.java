/*
 * Copyright (C) 2012 Wu Tong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cocoa4android.ui;

import android.graphics.Color;

public class UIColor {
	private int color;
	
	public static UIColor colorWithRed(float red,float green,float blue,float alpha){
		return new UIColor(red,green,blue,alpha);
	}
	
	public UIColor(int color){
		this.color = color;
	}
	public UIColor(float red,float green,float blue,float alpha){
		this.color = (int) (alpha*255);
		color = color<<8|((int) (red*255));
		color = color<<8|((int) (green*255));
		color = color<<8|((int) (blue*255));
	}
	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
	public static UIColor blackColor(){
		return new UIColor(Color.BLACK);
	}
	public static UIColor whiteColor(){
		return new UIColor(Color.WHITE);
	}
	public static UIColor clearColor(){
		return new UIColor(Color.TRANSPARENT);
	}
	public static UIColor grayColor(){
		return new UIColor(Color.GRAY);
	}
	public static UIColor darkGrayColor(){
		return new UIColor(Color.DKGRAY);
	}
	public static UIColor lightGrayColor(){
		return new UIColor(Color.LTGRAY);
	}
	
	public static UIColor blueColor(){
		return new UIColor(Color.BLUE);
	}
	public static UIColor greenColor(){
		return new UIColor(Color.GREEN);
	}
	public static UIColor redColor(){
		return new UIColor(Color.RED);
	}
	public static UIColor yellowColor(){
		return new UIColor(Color.YELLOW);
	}
	
}
