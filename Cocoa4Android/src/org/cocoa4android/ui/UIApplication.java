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

import org.cocoa4android.ns.NSObject;

import android.app.Activity;
import android.content.Context;

public class UIApplication extends NSObject{
	private AppDelegate delegate;
	
	private static UIApplication sharedApplication = null;
	
	private boolean isApplicationLaunched = NO;
	
	public boolean isApplicationLaunched() {
		return isApplicationLaunched;
	}
	public void setApplicationLaunched(boolean isApplicationLaunched) {
		this.isApplicationLaunched = isApplicationLaunched;
	}
	public static UIApplication sharedApplication() {
		if(sharedApplication==null){
			sharedApplication = new UIApplication();
		}
		return sharedApplication;
	}
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
	public AppDelegate delegate() {
		return delegate;
	}
	public void setDelegate(AppDelegate delegate) {
		this.delegate = delegate;
	}
	public Activity getActivity(){
		return (Activity) this.delegate;
	}
	private Context context = null;
	
	private UIWindow window;

	public void setWindow(UIWindow window){
		this.window = window;
	}
	public UIWindow getWindow() {
		return window;
	}
	
	
}
