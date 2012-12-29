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

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class UIAlertView {
	
	private UIAlertViewDelegate delegate = null;
	
	public UIAlertViewDelegate delegate() {
		return delegate;
	}


	public void setDelegate(UIAlertViewDelegate delegate) {
		this.delegate = delegate;
	}


	private String title = null;
	public String title() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String message() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}

	private String message = null;
	
	private int numberOfButtons;
	
	
	public int numberOfButtons() {
		return numberOfButtons;
	}


	public void setNumberOfButtons(int numberOfButtons) {
		this.numberOfButtons = numberOfButtons;
	}
	
	private int cancelButtonIndex=-1;

	public int cancelButtonIndex() {
		return cancelButtonIndex;
	}


	public void setCancelButtonIndex(int cancelButtonIndex) {
		this.cancelButtonIndex = cancelButtonIndex;
	}
	
	private int firstOtherButtonIndex=-1;

	public int firstOtherButtonIndex() {
		return firstOtherButtonIndex;
	}


	public void setFirstOtherButtonIndex(int firstOtherButtonIndex) {
		this.firstOtherButtonIndex = firstOtherButtonIndex;
	}
	private int buttonIndex = 0;
	private AlertDialog.Builder builder;
	public UIAlertView(String title,String message,UIAlertViewDelegate delegate,String cancelButtonTitle,String ...otherButtonTitles){
		this.delegate = delegate;
		this.message = message;
		this.title = title;
		builder = new Builder(UIApplication.sharedApplication().getContext());
		builder.setMessage(message);
		builder.setTitle(title);
		
		
		
		builder.setNegativeButton(cancelButtonTitle, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(UIAlertView.this.delegate!=null){
					UIAlertView.this.delegate.alertViewCancel(UIAlertView.this);
					UIAlertView.this.delegate.clickedButtonAtIndex(UIAlertView.this, cancelButtonIndex);
				}
				dialog.dismiss();
			}
		});
		if (otherButtonTitles!=null&&otherButtonTitles.length>0) {
			firstOtherButtonIndex = 0;
			for (int i = 0; i < otherButtonTitles.length; i++) {
				buttonIndex = i;
				builder.setNeutralButton(otherButtonTitles[i], new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(UIAlertView.this.delegate!=null){
							UIAlertView.this.delegate.clickedButtonAtIndex(UIAlertView.this, buttonIndex);
						}
						dialog.dismiss();
					}
				});
			}
		}
		
	}
	public void show(){
		builder.create().show();
	}
	
	public interface UIAlertViewDelegate{
		public void clickedButtonAtIndex(UIAlertView alertView,int buttonIndex);
		public void alertViewCancel(UIAlertView alertView);
	}
}
