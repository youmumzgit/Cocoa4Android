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

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class UIWindow extends UIView {
	public UIWindow(){
		this.setView(new WindowView(context));
		params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		params.alignWithParent = true;
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		params.leftMargin = 0;
		params.topMargin = 0;
		this.getView().setLayoutParams(params);
		
		this.setBackgroundColor(UIColor.whiteColor());
	}
	public UIWindow(CGRect frame){
		this();
	}
	private UIViewController rootViewController;
	public void makeKeyAndVisible(){
		UIApplication.sharedApplication().getActivity().setContentView(this.getView());
		UIApplication.sharedApplication().setWindow(this);
	}
	public UIViewController rootViewController() {
		return rootViewController;
	}
	public void setRootViewController(UIViewController rootViewController) {
		this.rootViewController = rootViewController;
		this.addSubview(rootViewController.view());
	}
	public class WindowView extends RelativeLayout{

		public WindowView(Context context) {
			super(context);
		}
		@Override
		protected void onSizeChanged(int widthNew, int heightNew, int widthOld, int heightOld){
			super.onSizeChanged(widthNew, heightNew, widthOld, heightOld);
			//fix applicationFrame
			UIScreen.mainScreen().setApplicationFrame(CGRectMake(0, 0, widthNew, heightNew+1));
			if (widthNew>0&&heightNew>0) {
				AppDelegate delegate =  UIApplication.sharedApplication().delegate();
				if (!UIApplication.sharedApplication().isApplicationLaunched()) {
					delegate.launchApplication();
				}
			}
		}
	}
}
