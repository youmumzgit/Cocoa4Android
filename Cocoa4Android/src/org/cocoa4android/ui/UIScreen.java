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

import java.security.PublicKey;

import org.cocoa4android.cg.CGRect;
import org.cocoa4android.cg.CGSize;
import org.cocoa4android.ns.NSObject;

import android.util.DisplayMetrics;

public class UIScreen extends NSObject{
	private static final int LOW_DPI_STATUS_BAR_HEIGHT = 19;
	private static final int MEDIUM_DPI_STATUS_BAR_HEIGHT = 25;
	private static final int HIGH_DPI_STATUS_BAR_HEIGHT = 38;
	
	private static UIScreen mainScreen = null;
	public static UIScreen mainScreen(){
		if(mainScreen ==null){
			mainScreen = new UIScreen();
		}
		return mainScreen;
	}
	
	
	private CGRect bounds;
	private CGRect applicationFrame;
	
	private CGSize standardScreenSize = null;
	private CGRect standardBounds;
	private CGRect standardApplicationFrame;

	private float statusBarHeight = 0;
	
	
	private float density;
	private int densityDpi;
	
	private boolean useDip = false;

	public boolean isUseDip() {
		return useDip;
	}
	public void setUseDip(boolean useDip) {
		if(this.useDip!=useDip){
			//update applicationFrame;
			if(useDip){
				this.bounds.size.width /=this.density;
				this.bounds.size.height /=this.density;
				this.setBounds(bounds);
			}
			this.useDip = useDip;
		}
		
	}
	
	public CGRect bounds() {
		if(this.standardScreenSize==null){
			return bounds;
		}else{
			return this.standardBounds;
		}
	}
	public void setBounds(CGRect bounds) {
		this.bounds = bounds;
		this.setApplicationFrame(new CGRect(0, 0, bounds.size.width, bounds.size.height-this.getStatusBarHeight()));
	}
	
	public CGRect applicationFrame() {
		if(this.standardScreenSize==null){
			return applicationFrame;
		}else{
			return this.standardApplicationFrame;
		}
	}
	public void setApplicationFrame(CGRect applicationFrame) {
		this.applicationFrame = applicationFrame;
	}
	
	public float getDensityX() {
		float value = 1.0f;
		if(this.useDip){
			value = density;
		}
		if(standardScreenSize!=null){
			value *= applicationFrame.size.width/standardApplicationFrame.size.width;
		}
		return value;
	}
	public float getDensityY() {
		float value = 1.0f;
		if(this.useDip){
			value = density;
		}
		if(standardScreenSize!=null){
			value *= applicationFrame.size.height/standardApplicationFrame.size.height;
		}
		return value;
	}
	public void setDensity(float density) {
		this.density = density;
	}
	public float getStatusBarHeight(){
		if(this.useDip){
			return this.statusBarHeight/this.density;
		}
		return this.statusBarHeight;
	}
	
	public CGSize getStandardScreenSize() {
		return standardScreenSize;
	}
	/*
	 * set the standard screen size,once set this value,the screen will auto sized to fit the real screen
	 */
	public void setStandardScreenSize(CGSize standardScreenSize) {
		this.standardScreenSize = standardScreenSize;
		
		this.standardBounds = new CGRect(0,0,standardScreenSize.width,standardScreenSize.height);
		this.standardApplicationFrame = new CGRect(0,0,standardScreenSize.width,standardScreenSize.height-statusBarHeight);
		UIWindow window = UIApplication.sharedApplication().delegate().window;
		window.setTransform(CGAffineTransformMakeScale(this.applicationFrame.size.width/this.standardApplicationFrame.size.width, this.applicationFrame.size.height/this.standardApplicationFrame.size.height));
	}
	
	public float getDensityDpi() {
		return densityDpi;
	}
	public void setDensityDpi(int densityDpi) {
		this.densityDpi = densityDpi;
		switch (this.densityDpi) {
	    case DisplayMetrics.DENSITY_HIGH:
	        statusBarHeight = HIGH_DPI_STATUS_BAR_HEIGHT;
	        break;
	    case DisplayMetrics.DENSITY_MEDIUM:
	        statusBarHeight = MEDIUM_DPI_STATUS_BAR_HEIGHT;
	        break;
	    case DisplayMetrics.DENSITY_LOW:
	        statusBarHeight = LOW_DPI_STATUS_BAR_HEIGHT;
	        break;
	    default:
	        statusBarHeight = MEDIUM_DPI_STATUS_BAR_HEIGHT;
		}
	}
}
