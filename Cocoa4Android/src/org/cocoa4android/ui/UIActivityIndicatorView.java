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
	public ProgressBar getActivityIndicatorView() {
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
