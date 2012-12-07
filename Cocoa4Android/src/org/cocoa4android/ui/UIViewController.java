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

import android.R.bool;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;

public class UIViewController extends NSObject{
	protected static boolean isTransition = false;
	
	protected UIView view;
	
	protected UIView otherViews;
	
	protected String title;
	
	
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
	protected void viewDidAppear(Boolean animated){
		
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



	protected UITabBarController tabBarController;
	protected UINavigationController navigationController;
	protected UIViewController parentViewController;
	
	protected UIViewController presentingViewController;
	
	protected UIViewController presentedViewController;
	
	public void presentModalViewController(UIViewController viewController,boolean animated){
		if (!isTransition) {
			isTransition = YES;
			UIView modalView = viewController.getView();
			modalView.setFrame(UIScreen.mainScreen().applicationFrame());
			modalView.setBackgroundColor(UIColor.whiteColor());
			
			UIApplication.sharedApplication().delegate().window.addSubview(modalView);
			this.setPresentedViewController(viewController);
			
			if(animated){
				this.translateBetweenViews(viewController.getView(), true);
			}else{
				this.getView().setHidden(true);
				viewController.viewDidAppear(NO);
				isTransition = NO;
			}
			viewController.setParentViewController(this);
			
		}
		
	}
	public void dismissModalViewController(boolean animated){
		this.dismissModalViewController(animated, true);
	}
	protected void dismissModalViewController(boolean animated,boolean checkParent){
		if (!isTransition) {
			isTransition = YES;
			if(this.presentedViewController()!=null){
				this.getView().setHidden(false);
				if(animated){
					this.translateBetweenViews(this.presentedViewController().getView(), false);
				}else{
					this.presentedViewController().getView().removeFromSuperView();
					this.setPresentedViewController(null);
					this.viewDidAppear(NO);
					isTransition = NO;
				}
			}else if(checkParent&&this.parentViewController()!=null){
				isTransition = NO;
				this.parentViewController().dismissModalViewController(animated,false);
			}
		}
		
	}
	private void translateBetweenViews(UIView modalView ,boolean isPresent){
		//œ‘ æ
		this.isPresent = isPresent;
		
		float densityY = UIScreen.mainScreen().getDensityY();
		
		CGRect applicationFrame = UIScreen.mainScreen().applicationFrame();
		float applicationheight = applicationFrame.size().height()*densityY;
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
					UIViewController.this.presentedViewController.viewDidAppear(YES);
				}else{
					UIViewController.this.presentedViewController().getView().removeFromSuperView();
					UIViewController.this.setPresentedViewController(null);
					UIViewController.this.viewDidAppear(YES);
				}
				isTransition = NO;
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

	public UIViewController presentedViewController() {
		return presentedViewController;
	}

	public void setPresentedViewController(UIViewController presentedViewController) {
		if(this.presentedViewController!=null){
			this.presentedViewController.setPresentingViewController(null);
		}
		this.presentedViewController = presentedViewController;
		if(presentedViewController!=null){
			presentedViewController.setPresentingViewController(this);
		}
	}

	public UIViewController presentingViewController() {
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
