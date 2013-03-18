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
import org.cocoa4android.ns.NSMutableArray;

public class UITabBarController extends UIViewController {

	private NSArray viewControllers;
	protected UITabBar tabBar;
	private UIView contentView;
	private CGRect appFrame = null;
	private int selectedIndex=-1;
	//private int previousSelectedIndex = -1;
	
	public UITabBarController(){
		super();
		appFrame = UIScreen.mainScreen().applicationFrame();				 
		float tabBarHeight = appFrame.size.height/10.0f;
		contentView = new UIView(CGRectMake(0,0,appFrame.size().width(),appFrame.size().height()-tabBarHeight));
		this.view.addSubview(contentView);
		this.initTabBar();
		
	}
	
	private void initTabBar(){
		tabBar = new UITabBar();
		tabBar.tabBarController = this;
		this.view.addSubview(tabBar);
	}
	public NSArray getViewControllers() {
		return viewControllers;
	}

	public void setViewControllers(NSArray viewControllers) {
		if(viewControllers!=null&&viewControllers.count() > 0){
			this.viewControllers = viewControllers;
			NSMutableArray items = NSMutableArray.array();
			for(int i = 0;i<viewControllers.count();i++){
				UIViewController viewController = (UIViewController) viewControllers.objectAtIndex(i);
				viewController.setTabBarController(this);
				items.addObject(viewController.tabBarItem());
			}
			this.tabBar.setItems(items);
			this.loadViewController(0);
			//default select the first one
			//this.setSelectedIndex(0);
			this.selectedIndex = 0;
			
			UITabBarItem item = (UITabBarItem) items.objectAtIndex(0);
			this.tabBar.setSelectedItem(item);
			item.itemButton.setSelected(YES);
		}
	}
	void setTabBarItem(UITabBarItem tabBarItem,UIViewController viewController){
		int changeIndex = -1;
		if(viewControllers!=null){
			for(int i = 0;i<viewControllers.count();i++){
				if (viewController==viewControllers.objectAtIndex(i)) {
					changeIndex = i;
					break;
				}
			}
		}
		if (changeIndex!=-1) {
			this.tabBar.setItemAtIndex(tabBarItem, changeIndex);
		}
	}
	private boolean loadViewController(int index){
		if(index<this.viewControllers.count()){
			UIViewController viewController = (UIViewController) viewControllers.objectAtIndex(index);
			UIView view = viewController.view();
			if(view.superview()==null){
				contentView.addSubview(view);
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
	public boolean onBackPressed(){
		if (super.onBackPressed()) {
			return YES;
		}
		//current node
		if(this.viewControllers!=null&&this.viewControllers.count()>0){
			//current node
			UIViewController viewController = (UIViewController) this.viewControllers.objectAtIndex(this.selectedIndex);
			if(viewController.onBackPressed()){
				return YES;
			}
		}
		return NO;
	}

}
