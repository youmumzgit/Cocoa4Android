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

import org.cocoa4android.cg.CGAffineTransform;
import org.cocoa4android.cg.CGRect;
import org.cocoa4android.ns.NSObject;

import android.content.Context;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;



public class UIView extends NSObject{
	private UIView superView;
	private View view;
	private boolean isHidden;
	protected CGRect frame;
	protected CGAffineTransform transform;
	
	private UIColor backgroundColor;
	protected float densityX = UIScreen.mainScreen().getDensityX();
	protected float densityY = UIScreen.mainScreen().getDensityY();
	
	protected Context context = UIApplication.sharedApplication().getContext();
	protected LayoutInflater inflater;
	private int tag;
	public UIView(){
		//if no setting fill the parent
		this.setView(new RelativeLayout(context));
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		params.alignWithParent = true;
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		params.leftMargin = 0;
		params.topMargin = 0;
		this.view.setLayoutParams(params);
	}
	public UIView(int viewid){
		inflater = LayoutInflater.from(context);  
		this.setView(inflater.inflate(viewid, null));
	}
	public UIView(CGRect frame){
		this.setView(new RelativeLayout(context));
		this.setFrame(frame);
	}
	public UIView(View view){
		this.setView(view);
	}
	public void addSubView(UIView child){
		if(this.isViewGroup()){
			ViewGroup vg = (ViewGroup)this.view;
			vg.addView(child.getView());
			child.setSuperView(this);
		}
	}
	public void removeSubView(UIView child){
		if(this.isViewGroup()){
			ViewGroup vg = (ViewGroup)this.view;
			vg.removeView(child.getView());
			child.setSuperView(null);
		}
	}
	public void removeFromSuperView(){
		if(this.superView!=null){
			this.superView.removeSubView(this);
		}
	}
	public View getView(){
		return this.view;
	}
	public void setView(View view){
		this.view = view;
		/*
		if(view!=null){
			this.view.setOnTouchListener(new OnTouchListener(){

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if(UIView.this.getView().equals(v)){
						UITouch[] touches = new UITouch[event.getPointerCount()];
						for(int i=0;i<touches.length;i++){
							float x = event.getX(i);
							float y = event.getY(i);
							float prevX = 0;
							float prevY = 0;
							if(event.getHistorySize()>0){
								prevX = event.getHistoricalX(i, 0);
								prevY = event.getHistoricalY(i, 0);
							}
							touches[i] = new UITouch(x,y,prevX,prevY,new UIView(v));
						}
					
						// TODO Auto-generated method stub
						if(event.getAction()==MotionEvent.ACTION_DOWN){
							UIView.this.touchesBegan(touches,event);
						}else if(event.getAction()==MotionEvent.ACTION_MOVE){
							UIView.this.touchesMoved(touches,event);
						}else if(event.getAction()==MotionEvent.ACTION_UP){
							UIView.this.touchesEnded(touches,event);
						}
					}
					return false;
				}
				
			});
		}
		*/
	}
	public UIView superView() {
		return superView;
	}
	public void setSuperView(UIView superView) {
		this.superView = superView;
	}
	public boolean isHidden() {
		return isHidden;
	}
	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
		this.view.setVisibility(isHidden?View.INVISIBLE:View.VISIBLE);
	}
	public CGRect frame() {
		if(frame==null){
			float width = this.getView().getWidth()/densityX;
			float height = this.getView().getHeight()/densityY;
			LayoutParams params = (LayoutParams) this.getView().getLayoutParams();
			float x = 0;
			float y = 0;
			if(params!=null){
				x = params.leftMargin/densityX;
				y = params.topMargin/densityY;
			}
			frame = new CGRect(x,y,width,height);
		}
		return frame;
	}
	public void setFrame(CGRect frame) {
		this.frame = frame;
		LayoutParams params = new LayoutParams((int)(frame.size().width()*densityX), (int)(frame.size().height()*densityY));
		params.alignWithParent = true;
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		params.leftMargin = (int)(frame.origin().x()*densityX);
		params.topMargin = (int)(frame.origin().y()*densityY);
		this.view.setLayoutParams(params);
	}
	
	public CGAffineTransform getTransform() {
		return transform;
	}
	/**
	 * change the size and position of the shape
	 * @param transform 
	 */
	public void setTransform(CGAffineTransform transform) {
		this.transform = transform;
	}
	
	public UIColor backgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(UIColor backgroundColor) {
		this.backgroundColor = backgroundColor;
		this.view.setBackgroundColor(backgroundColor.getColor());
	}
	protected boolean isViewGroup(){
		return ViewGroup.class.isInstance(this.view);
	}
	public int tag() {
		return tag;
	}
	public void setTag(int tag) {
		this.tag = tag;
	}
	public void setBackgroundImage(UIImage backgroundImage){
		this.view.setBackgroundResource(backgroundImage.getResId());
	}
	public void bringSubviewToFront(UIView view){
		if(this.isViewGroup()){
			ViewGroup vg = (ViewGroup)this.view;
			vg.bringChildToFront(view.getView());
		}
		//view.getView().bringToFront();
	}
	public void touchesBegan(UITouch[] touches,MotionEvent event){
		
	}
	public void touchesEnded(UITouch[] touches,MotionEvent event){
		
	}
	public void touchesMoved(UITouch[] touches,MotionEvent event){
		
	}
}
