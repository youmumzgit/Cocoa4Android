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

import org.cocoa4android.cg.CGRect;
import org.cocoa4android.cg.CGSize;
import org.cocoa4android.ns.NSObject;

import android.util.DisplayMetrics;

public class UIScreen extends NSObject{
	private static final boolean AUTOSIZE = YES;
	private static final boolean USEDIP = NO;
	
	private static UIScreen mainScreen = null;
	public static UIScreen mainScreen(){
		if(mainScreen ==null){
			mainScreen = new UIScreen();
		}
		return mainScreen;
	}
	
	public void setDisplayMetrics(DisplayMetrics dm) {
		this.setDensity(dm.density);
		UIView.density = this.getDensity();
		this.setDensityDpi(dm.densityDpi);
		this.setBounds(CGRectMake(0, 0, dm.widthPixels, dm.heightPixels));
		this.setDensityText(dm.scaledDensity);
		
	}
	
	//================================================================================
    // Screen Size
    //================================================================================
	CGRect bounds;
	CGRect applicationFrame;
	
	public CGRect bounds() {
		if(this.standardScreenSize==null){
			return bounds;
		}else{
			return this.standardBounds;
		}
	}
	private void setBounds(CGRect bounds) {
		this.bounds = CGRectMake(0, 0, bounds.size.width/density, bounds.size.height/density);
	}
	
	public CGRect applicationFrame() {
		if(this.standardScreenSize==null){
			return applicationFrame;
		}else{
			return this.standardApplicationFrame;
		}
	}
	void setApplicationFrame(CGRect applicationFrame) {
		this.applicationFrame = CGRectMake(0, 0, applicationFrame.size.width/density, applicationFrame.size.height/density);
	}
	
	//================================================================================
    // Standard Size
    //================================================================================
	private CGSize standardScreenSize = null;
	private CGRect standardBounds;
	private CGRect standardApplicationFrame;
	
	
	public CGSize getStandardScreenSize() {
		return standardScreenSize;
	}
	/*
	 * set the standard screen size,once set this value,the screen will auto resized to fit the real screen
	 */
	public void setStandardScreenSize(CGSize standardScreenSize) {
		this.standardScreenSize = standardScreenSize;
		if (standardScreenSize!=null) {
			this.standardBounds = new CGRect(0,0,standardScreenSize.width,standardScreenSize.height);
			this.standardApplicationFrame = new CGRect(0,0,standardScreenSize.width,standardScreenSize.height);
			
			UIView.scaleFactorX = this.getScaleFactorX();
			UIView.scaleFactorY = this.getScaleFactorY();
			UIView.scaleDensityX = UIView.scaleFactorX*UIView.density;
			UIView.scaleDensityY = UIView.scaleFactorY*UIView.density;
		}else{
			this.standardBounds = null;
			this.standardApplicationFrame = null;
		}
		
	}
	
	//================================================================================
    // Density
    //================================================================================
	private float density;
	private int densityDpi;
	private float densityText;
	
	public float getDensity(){
		return density;
	}
	private void setDensity(float density) {
		if (USEDIP) {
			this.density = density;
		}else{
			this.density = 1.0f;
		}
		
	}
	
	
	public float getDensityDpi() {
		return densityDpi;
	}
	private void setDensityDpi(int densityDpi) {
		this.densityDpi = densityDpi;
	}
	
	public float getDensityText() {
		float value = 1.0f;
		value = densityText;
		/*
		if(USEDIP){
			value = densityText;
		}
		if(standardScreenSize!=null&&AUTOSIZE){
			value *= applicationFrame.size.height/standardApplicationFrame.size.height;
		}
		*/
		return value;
	}
	private void setDensityText(float densityText) {
		this.densityText = densityText;
	}
	
	
	//================================================================================
    // Scale
    //================================================================================
	public float getScaleFactorX(){
		float value = 1.0f;
		if(standardScreenSize!=null&&AUTOSIZE){
			value *= applicationFrame.size.width/standardApplicationFrame.size.width;
		}
		return value;
	}
	public float getScaleFactorY(){
		float value = 1.0f;
		if(standardScreenSize!=null&&AUTOSIZE){
			value *= applicationFrame.size.height/standardApplicationFrame.size.height;
		}
		return value;
	}
	
	
	
	
	
	/*
	private boolean useDip = NO;

	public boolean isUseDip() {
		return useDip;
	}
	//recommend to use pixel 
	//so make it private
	private void setUseDip(boolean useDip) {
		if(this.useDip!=useDip){
			//update applicationFrame;
			if(useDip){
				this.bounds.size.width /=this.density;
				this.bounds.size.height /=this.density;
				this.setBounds(bounds);
				this.setStandardScreenSize(null);
			}
			this.useDip = useDip;
			UIView.density = this.getDensity();
		}
		
	}
	*/
}
