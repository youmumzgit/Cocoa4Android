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

import android.widget.ProgressBar;

public class UIActivityIndicatorView extends UIView {
	private ProgressBar activityIndicatorView = null;
	
	public UIActivityIndicatorView(){
		ProgressBar activityIndicatorView = new ProgressBar(context);
		this.setActivityIndicatorView(activityIndicatorView);
		this.setView(activityIndicatorView);
	}
	public UIActivityIndicatorView(CGRect frame){
		this();
		this.setFrame(frame);
	}
	protected ProgressBar getActivityIndicatorView() {
		return activityIndicatorView;
	}

	public void setActivityIndicatorView(ProgressBar activityIndicatorView) {
		this.activityIndicatorView = activityIndicatorView;
	}
	public void startAnimating(){
		this.setHidden(false);
	}
	public void stopAnimating(){
		this.setHidden(true);
	}
	
}
