package org.cocoa4android.ui;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import org.cocoa4android.cg.CGRect;
import org.cocoa4android.util.CAObjectMethod;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;


public class UIControl extends UIView {
	
	protected UIControlState currentState = UIControlState.UIControlStateNormal;
	
	protected HashMap<UIControlEvent,ArrayList<CAObjectMethod>> callbacks = new HashMap<UIControlEvent,ArrayList<CAObjectMethod>>();
	public UIControl() {
		super();
	}
	public UIControl(CGRect frame) {
		super(frame);
	}
	public void setView(View view) {
		super.setView(view);
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ArrayList<CAObjectMethod> oms = callbacks.get(UIControlEvent.UIControlEventTouchUpInside);
				if(oms!=null){
					for(int i=0;i<oms.size();i++){
						//loop to call the callbacks
						CAObjectMethod om = oms.get(i);
						om.invoke(UIControl.this);
					}
				}
			}
		});
	}
	public void addEventListener(Object target,String selector,UIControlEvent controlEvent){
		ArrayList<CAObjectMethod> ams = callbacks.get(controlEvent);
		if(ams==null){
			ams = new ArrayList<CAObjectMethod>();
			callbacks.put(controlEvent,ams);
		}
		ams.add(new CAObjectMethod(target,selector));
		
	}
	
	protected UIControlState getCurrentState() {
		return currentState;
	}
	protected void setCurrentState(UIControlState currentState) {
		this.currentState = currentState;
	}
	
	public void touchesEnded(UITouch[] touches,MotionEvent event){
		ArrayList<CAObjectMethod> oms = callbacks.get(UIControlEvent.UIControlEventTouchUpInside);
		if(oms!=null){
			for(int i=0;i<oms.size();i++){
				//loop to call the callbacks
				CAObjectMethod om = oms.get(i);
				om.invoke(this);
			}
		}
	}
	
}
