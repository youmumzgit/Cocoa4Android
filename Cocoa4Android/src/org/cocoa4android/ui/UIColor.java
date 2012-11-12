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
