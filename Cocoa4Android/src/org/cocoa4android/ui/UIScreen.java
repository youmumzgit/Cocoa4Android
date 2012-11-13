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
import org.cocoa4android.ns.NSObject;

public class UIScreen extends NSObject{
	private CGRect bounds;
	private CGRect applicationFrame;
	private float density;
	private static UIScreen mainScreen = null;
	public static UIScreen getMainScreen(){
		if(mainScreen ==null){
			mainScreen = new UIScreen();
		}
		return mainScreen;
	}
	public CGRect getBounds() {
		return bounds;
	}
	public void setBounds(CGRect bounds) {
		this.bounds = bounds;
	}
	public CGRect getApplicationFrame() {
		return applicationFrame;
	}
	public void setApplicationFrame(CGRect applicationFrame) {
		this.applicationFrame = applicationFrame;
	}
	public float getDensity() {
		return density;
	}
	public void setDensity(float density) {
		this.density = density;
	}
}
