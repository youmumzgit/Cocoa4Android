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
import org.cocoa4android.ns.NSArray;

public class UITabBarController extends UIViewController {

	private NSArray viewControllers;
	protected UIView tabBar;
	private UIView container;
	
	private int selectedIndex=-1;
	//private int previousSelectedIndex = -1;
	
	public UITabBarController(){
		super();
		//add two part of view					 
		int tabBarHeight = 40;
		CGRect frame = UIScreen.mainScreen().applicationFrame();
		container = new UIView(new CGRect(0,0,frame.size().width(),frame.size().height()-tabBarHeight));
		this.view.addSubview(container);
		
		//default set tabBar 49
		tabBar = new UIView(new CGRect(0,frame.size().height()-tabBarHeight,frame.size().width(),tabBarHeight));
		tabBar.setBackgroundColor(UIColor.blackColor());
		this.view.addSubview(tabBar);
	}
	
	public NSArray getViewControllers() {
		return viewControllers;
	}

	public void setViewControllers(NSArray viewControllers) {
		if(viewControllers!=null&&viewControllers.count() > 0){
			this.viewControllers = viewControllers;
			
			for(int i = 0;i<viewControllers.count();i++){
				UIViewController viewController = (UIViewController) viewControllers.objectAtIndex(i);
				viewController.setTabBarController(this);
			}
			this.loadViewController(0);
			//default select the first one
			//this.setSelectedIndex(0);
			this.selectedIndex = 0;
		}
	}
	private boolean loadViewController(int index){
		if(index<this.viewControllers.count()){
			UIViewController viewController = (UIViewController) viewControllers.objectAtIndex(index);
			UIView view = viewController.view();
			if(view.superView()==null){
				container.addSubview(view);
			}
			if(view.isHidden()){
				view.setHidden(NO);
			}
			return true;
		}
		return false;
	}
	
	public int getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(int selectedIndex) {
		if(selectedIndex!=this.selectedIndex){
			if(this.viewControllers!=null&&this.loadViewController(selectedIndex)){
				if(this.selectedIndex!=-1){
					UIViewController viewController = (UIViewController) this.viewControllers.objectAtIndex(this.selectedIndex);
					viewController.view().setHidden(YES);
				}
				//previousSelectedIndex = this.selectedIndex;
				this.selectedIndex = selectedIndex;
			}
		}
	}
	public UIView tabBar() {
		return tabBar;
	}
	
	@Override
	public boolean backKeyDidClicked(){
		if(!super.backKeyDidClicked()){
			return false;
		}
		//current node
		if(this.viewControllers!=null&&this.viewControllers.count()>0){
			//current node
			UIViewController viewController = (UIViewController) this.viewControllers.objectAtIndex(this.selectedIndex);
			if(!viewController.backKeyDidClicked()){
				return false;
			}
		}
		return true;
	}

}
