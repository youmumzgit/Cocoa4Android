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
import org.cocoa4android.ns.NSSet;

import android.view.MotionEvent;

public class UIResponder extends NSObject {
	//================================================================================
    // Touch Event
    //================================================================================
	
	public void touchesBegan(NSSet touches,UIEvent event){

	}
	public void touchesEnded(NSSet touches,UIEvent event){

	}
	public void touchesMoved(NSSet touches,UIEvent event){

	}
	public void touchesCancelled(NSSet touches,UIEvent event){

	}
	
	protected void handleTouch(MotionEvent event){
		
	}
	
	//================================================================================
    // First Responder
    //================================================================================
	protected static UIResponder firstResponder = null;
	// default is NO
	public boolean canBecomeFirstResponder(){
		return NO;
	}   
	public boolean becomeFirstResponder(){
		if (this.canBecomeFirstResponder()) {
			firstResponder = this;
			return YES;
		}
		return NO;
	}
	// default is YES
	public boolean canResignFirstResponder(){
		return YES;
	}
	public boolean resignFirstResponder(){
		if (this.canResignFirstResponder()) {
			firstResponder = null;
			return YES;
		}
		return NO;
	}
	public boolean isFirstResponder(){
		
		return firstResponder==this;
	}
}
