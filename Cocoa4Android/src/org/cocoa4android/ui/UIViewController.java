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
import org.cocoa4android.ns.NSObject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;

public class UIViewController extends NSObject{
	protected UIView view;
	
	protected UIView otherViews;
	
	private String title;
	
	
	private boolean isPresent;
	
	public UIView getView() {
		return view;
	}

	public void setView(UIView view) {
		this.view = view;
		if(view!=null){
			this.view.getView().postDelayed(new Runnable(){
	
				@Override
				public void run() {
					// TODO Auto-generated method stub
					UIViewController.this.viewDidLoad();
				}
			},200);
		}
	}
	protected void viewDidLoad(){
		
	}
	protected int viewid;
	protected LayoutInflater inflater;
	
	/**
	 * if not argument create empty view
	 */
	public UIViewController(){
		this.setView(new UIView());
	}
	public UIViewController(int viewid){
		this.viewid = viewid;
		inflater = LayoutInflater.from(UIApplication.sharedApplication().getContext());  
		View v =  inflater.inflate(viewid, null);
		this.setView(new UIView(v));
	}
	public UIViewController(String xib){
		
	}


	public UINavigationController getNavigationController() {
		return navigationController;
	}

	public void setNavigationController(UINavigationController navigationController) {
		this.navigationController = navigationController;
		this.setParentViewController(navigationController);
	}


	public UIViewController parentViewController() {
		return parentViewController;
	}

	public void setParentViewController(UIViewController parentViewController) {
		this.parentViewController = parentViewController;
	}



	public UITabBarController tabBarController() {
		return tabBarController;
	}

	public void setTabBarController(UITabBarController tabBarController) {
		this.tabBarController = tabBarController;
		this.setParentViewController(tabBarController);
	}



	private UITabBarController tabBarController;
	private UINavigationController navigationController;
	private UIViewController parentViewController;
	
	private UIViewController presentingViewController;
	private UIViewController presentedViewController;
	
	public void presentModalViewController(UIViewController viewController,boolean animated){
		UIView modalView = viewController.getView();
		modalView.setFrame(UIScreen.getMainScreen().getApplicationFrame());
		modalView.setBackgroundColor(UIColor.whiteColor());
		
		UIApplication.sharedApplication().delegate().window.addSubView(modalView);
		
		if(animated){
			this.setPresentingViewController(viewController);
			this.translateBetweenViews(viewController.getView(), true);
		}else{
			this.getView().setHidden(true);
			this.setPresentedViewController(viewController);
			
		}
		viewController.setParentViewController(this);
	}
	public void dismissModalViewController(boolean animated){
		this.dismissModalViewController(animated, true);
	}
	protected void dismissModalViewController(boolean animated,boolean checkParent){
		if(this.getPresentedViewController()!=null){
			this.getView().setHidden(false);
			if(animated){
				this.translateBetweenViews(this.getPresentedViewController().getView(), false);
			}else{
				this.getPresentedViewController().getView().removeFromSuperView();
				this.setPresentedViewController(null);
			}
		}else if(checkParent&&this.parentViewController()!=null){
			this.parentViewController().dismissModalViewController(animated,false);
		}
	}
	private void translateBetweenViews(UIView modalView ,boolean isPresent){
		//œ‘ æ
		this.isPresent = isPresent;
		
		float density = UIScreen.getMainScreen().getDensity();
		
		CGRect applicationFrame = UIScreen.getMainScreen().getApplicationFrame();
		float applicationheight = applicationFrame.size().height()*density;
		TranslateAnimation animation = null;
		if(isPresent){
			animation = new TranslateAnimation(0,0,applicationheight,0);
		}else{
			animation = new TranslateAnimation(0,0,0,applicationheight);
		}
		
		
		
		animation.setDuration(500);
		modalView.getView().startAnimation(animation);
		animation.startNow();
		
		
		animation.setAnimationListener(new AnimationListener(){

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				//HIDE
				//UINavigationController.this.getView().setHidden(true);
				if(UIViewController.this.isPresent){
					UIViewController.this.getView().setHidden(true);
					UIViewController.this.setPresentedViewController(UIViewController.this.getPresentingViewController());
					UIViewController.this.setPresentingViewController(null);
				}else{
					UIViewController.this.getPresentedViewController().getView().removeFromSuperView();
					UIViewController.this.setPresentedViewController(null);
				}
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}

	public UIViewController getPresentedViewController() {
		return presentedViewController;
	}

	public void setPresentedViewController(UIViewController presentedViewController) {
		this.presentedViewController = presentedViewController;
	}

	public UIViewController getPresentingViewController() {
		return presentingViewController;
	}

	public void setPresentingViewController(UIViewController presentingViewController) {
		this.presentingViewController = presentingViewController;
	}
	
	public boolean backKeyDidClicked(){
		if(this.presentedViewController!=null){
			this.dismissModalViewController(true);
			return false;
		}
		return true;
	}

	public String title() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
