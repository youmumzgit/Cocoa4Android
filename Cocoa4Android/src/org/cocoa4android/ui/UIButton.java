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


import java.util.HashMap;

import org.cocoa4android.cg.CGRect;
import org.cocoa4android.ns.NSString;

import android.view.MotionEvent;
import android.widget.Button;

public class UIButton extends UIControl {
	//private HashMap<UIControlState,String> images = new HashMap<UIControlState,String>();
	private boolean enable = true;
	private HashMap<UIControlState,String> titles = new HashMap<UIControlState,String>();
	private HashMap<UIControlState,UIImage> backgroundImages = new HashMap<UIControlState,UIImage>();
	
	protected Button button = null;
	public UIButton(){
		Button btn = new Button(context);
		this.setButton(btn);
		this.setView(button);
	}
	public UIButton(CGRect frame){
		this();
		this.setFrame(frame);
	}
	public Button getButton() {
		return button;
	}
	public void setTitle(NSString title){
		this.setTitle(title, UIControlState.UIControlStateNormal);
	}
	public void setTitle(String title){
		this.setTitle(title, UIControlState.UIControlStateNormal);
	}
	public void setTitle(String title,UIControlState state){
		titles.put(state, title);
		if(this.currentState==state){
			this.button.setText(title);
		}
	}
	public void setTitle(NSString title,UIControlState state){
		titles.put(state, title.getString());
		if(this.currentState==state){
			this.button.setText(title.getString());
		}
	}
	@Override
	public void setBackgroundImage(UIImage backgroundImage){
		this.setBackgroundImageForState(backgroundImage, UIControlState.UIControlStateNormal);
	}
	public void setBackgroundImageForState(UIImage backgroundImage,UIControlState state){
		backgroundImages.put(state, backgroundImage);
		if(this.currentState==state){
			if(backgroundImage.getResId()!=0){
				this.button.setBackgroundResource(backgroundImage.getResId());
			}else{
				this.button.setBackgroundDrawable(backgroundImage.getDrawable());
			}
		}
	}
	
	
	@Override
	public void setCurrentState(UIControlState currentState){
		if(this.currentState!=currentState){
			UIImage backgroundImage = backgroundImages.get(currentState);
			if(backgroundImage!=null){
				this.button.setBackgroundResource(backgroundImage.getResId());
			}
			
			String title = titles.get(currentState);
			if(currentState!=null){
				this.button.setText(title);
			}
			this.currentState = currentState;
		}
	}
	
	
	
	
	public void setButton(Button button) {
		this.button = button;
		/*
		if(button!=null){
			button.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					if(UIButton.this.enable){
						// TODO Auto-generated method stub
						//click on
						ArrayList<CAObjectMethod> oms = callbacks.get(UIControlEvent.UIControlEventTouchUpInside);
						if(oms!=null){
							for(int i=0;i<oms.size();i++){
								//loop to call the callbacks
								CAObjectMethod om = oms.get(i);
								om.invoke(UIButton.this);
							}
						}
					}
				}
				
			});
		}
		*/
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		if(this.enable != enable){
			if(enable){
				this.setCurrentState(UIControlState.UIControlStateNormal);
			}else{
				this.setCurrentState(UIControlState.UIControlStateDisabled);
			}
			this.enable = enable;
			this.button.setEnabled(enable);
		}
	}
	public void touchesEnded(UITouch[] touches,MotionEvent event){}
	
}
