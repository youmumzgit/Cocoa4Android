package org.cocoa4android.ui;

import android.content.Context;

public class UIApplication {
	private UIAppDelegate delegate;
	
	private static UIApplication sharedApplication = null;

	public static UIApplication getSharedApplication() {
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
	public UIAppDelegate getDelegate() {
		return delegate;
	}
	public void setDelegate(UIAppDelegate delegate) {
		this.delegate = delegate;
	}
	private Context context = null;
	
	
}
