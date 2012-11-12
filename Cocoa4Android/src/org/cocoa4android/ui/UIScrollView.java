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

import org.cocoa4android.cg.CGPoint;
import org.cocoa4android.cg.CGRect;
import org.cocoa4android.cg.CGSize;
import org.cocoa4android.extend.HVScrollView;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;

public class UIScrollView extends UIView {
	private HVScrollView scrollView = null;
	private UIView contentView = null;
	private CGPoint contentOffSet;
	private CGSize contentSize;
	private UIScrollViewDelegate delegate;
	private boolean moveBegan = false;
	private boolean pageEnabled = false;
	
	
	public UIScrollView() {
		scrollView = new HVScrollView(context);
		scrollView.setPadding(0, 0, 0, 0);
		contentView = new UIView();
		scrollView.addView(contentView.getView());
		this.setView(scrollView);
		contentOffSet = new CGPoint(0,0);
		contentSize = new CGSize(0,0);
		
		scrollView.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
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
					UIScrollView.this.touchesBegan(touches,event);
				}else if(event.getAction()==MotionEvent.ACTION_MOVE){
					UIScrollView.this.touchesMoved(touches,event);
				}else if(event.getAction()==MotionEvent.ACTION_UP){
					UIScrollView.this.touchesEnded(touches,event);
				}
				
				return false;
			}
			
		});
		//this.setShowsVerticalScrollIndicator(true);
		
	}
	public UIScrollView(CGRect frame) {
		this();
		this.setFrame(frame);
	}
	
	public void setContentOffSet(CGPoint contentOffSet) {
		this.scrollView.scrollTo((int)(contentOffSet.getX()*density), (int)(contentOffSet.getY()*density));
	}
	public CGPoint contentOffSet() {
		return new CGPoint(scrollView.getScrollX()/density,scrollView.getScrollY()/density);
	}
	
	public void setContentSize(CGSize contentSize) {
		this.contentSize = contentSize;
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				(int)(contentSize.getWidth()*density),
				(int)(contentSize.getHeight()*density)
			);
		
		this.contentView.getView().setLayoutParams(params);
	}
	public CGSize contentSize() {
		return this.contentSize;
	}
	@Override
	public void addSubView(UIView child) {
		contentView.addSubView(child);
	}
	@Override
	public void removeSubView(UIView child) {
		contentView.removeSubView(child);
	}
	public void scrollRectToVisible(CGRect frame,boolean animated){
		scrollView.scrollToChildRect(frame.getRect(),!animated);
		if(!animated){
			if(delegate!=null){
				delegate.scrollViewDidEndDecelerating(this);
			}
		}
		/*
		if(animated){
			scrollView.smoothScrollTo((int)frame.getOrigin().getX(), (int)frame.getOrigin().getY());
			scrollView.smoothScrollTo((int)frame.getOrigin().getX(), (int)frame.getOrigin().getY());
		}else{
			scrollView.scrollTo((int)frame.getOrigin().getX(), (int)frame.getOrigin().getY());
			scrollView.scrollTo((int)frame.getOrigin().getX(), (int)frame.getOrigin().getY());
			if(delegate!=null){
				delegate.scrollViewDidEndDecelerating(this);
			}
		}
		*/
	}
	public UIScrollViewDelegate getDelegate() {
		return delegate;
	}
	public void setDelegate(UIScrollViewDelegate delegate) {
		this.delegate = delegate;
	}
	@Override
	public void touchesBegan(UITouch[] touches,MotionEvent event){
		moveBegan = true;
	}
	@Override
	public void touchesMoved(UITouch[] touches,MotionEvent event){
		if(moveBegan){
			if(delegate!=null){
				delegate.scrollViewWillBeginDragging(this);
			}
			moveBegan = false;
		}
		if(delegate!=null){
			delegate.scrollViewDidScroll(this);
		}
	}
	
	@Override
	public void touchesEnded(UITouch[] touches,MotionEvent event){
		if(!moveBegan){
			if(delegate!=null){
				delegate.scrollViewDidEndDecelerating(this);
			}
			if(this.pageEnabled){
				//ajust to page
				
				CGRect frame = this.getFrame();
				int x  =  (int) (scrollView.getScrollX()/density);
				int y  =  (int) (scrollView.getScrollY()/density);
				
				float pageWidth = this.getFrame().getSize().getWidth();
				int pagew = (int) (Math.floor((this.contentOffSet().getX()-pageWidth/2)/pageWidth)+1);
				
				
				float pageHeight = this.getFrame().getSize().getHeight();
				int pageh = (int) (Math.floor((this.contentOffSet().getY()-pageHeight/2)/pageHeight)+1);
				
				int towardX = (int) (frame.getSize().getWidth()*pagew);
				int towardY = (int) (frame.getSize().getHeight()*pageh);
				
				scrollView.smoothScrollTo((int)(towardX*density), (int)(towardY*density));
				//scrollView.scrollTo((int)(towardX*density), (int)(towardY*density));
			}
		}
		moveBegan = false;
	}
	
	public interface UIScrollViewDelegate {
		public void scrollViewDidScroll(UIScrollView scrollView);
		
		public void scrollViewWillBeginDragging(UIScrollView scrollView);
		
		public void scrollViewDidEndDecelerating(UIScrollView scrollView);
		
	}
	
	public void setShowsHorizontalScrollIndicator(boolean showsHorizontalScrollIndicator){
		this.scrollView.setHorizontalScrollBarEnabled(showsHorizontalScrollIndicator);
	}
	
	public void setShowsVerticalScrollIndicator(boolean showsVerticalScrollIndicator){
		this.scrollView.setVerticalScrollBarEnabled(showsVerticalScrollIndicator);
	}
	public boolean isPageEnabled() {
		return pageEnabled;
	}
	public void setPageEnabled(boolean pageEnabled) {
		this.pageEnabled = pageEnabled;
		this.scrollView.setFlingEnabled(!pageEnabled);
	}
}

