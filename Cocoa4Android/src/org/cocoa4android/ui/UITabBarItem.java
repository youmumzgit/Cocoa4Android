package org.cocoa4android.ui;

import org.cocoa4android.R;
import org.cocoa4android.ui.UIControl.UIControlEvent;
import org.cocoa4android.ui.UIControl.UIControlState;

import android.view.Gravity;

public class UITabBarItem extends UIBarItem {
	UITabBar tabBar;
	int itemIndex = 0;
	public UITabBarItem(String title,UIImage image,int tag){
		this.setTitle(title);
		this.setImage(image);
		this.setTag(tag);
		this.init();
	}
	public UITabBarItem(UITabBarSystemItem systemItem,int tag){
		this.setTag(tag);
		this.init();
	}
	UIButton itemButton;
	private void init(){
		itemButton = new UIButton();
		itemButton.setBackgroundImage(UIImage.imageNamed(R.drawable.zz_c4a_tabbar_highlight), UIControlState.UIControlStateSelected);
		itemButton.titleLabel().setFontSize(6);
		itemButton.titleLabel().getLabel().setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL);
		itemButton.titleLabel().getLabel().setPadding(0, 0, 0, (int) (2*UIView.scaleFactorY));
		itemButton.setTitle(this.title());
		itemButton.setTitleColor(UIColor.grayColor());
		itemButton.setTitleColor(UIColor.whiteColor(), UIControlState.UIControlStateSelected);
		itemButton.addTarget(this, selector("select"), UIControlEvent.UIControlEventTouchUpInside);
		if (this.image()!=null) {
			itemButton.setImage(this.image().createTabBarImage(NO));
			itemButton.setImage(this.image().createTabBarImage(YES), UIControlState.UIControlStateSelected);
		}
	}
	public void select(){
		UITabBarItem selectedItem = this.tabBar.selectedItem();
		if (selectedItem!=null) {
			selectedItem.deselect();
		}
		this.tabBar.tabBarController.setSelectedIndex(itemIndex);
		this.tabBar.setSelectedItem(this);
		this.itemButton.setSelected(YES);
	}
	public void deselect(){
		this.itemButton.setSelected(NO);
		this.itemButton.imageView().getView().setBackgroundDrawable(null);
	}
	public enum UITabBarSystemItem{
		UITabBarSystemItemMore,
	    UITabBarSystemItemFavorites,
	    UITabBarSystemItemFeatured,
	    UITabBarSystemItemTopRated,
	    UITabBarSystemItemRecents,
	    UITabBarSystemItemContacts,
	    UITabBarSystemItemHistory,
	    UITabBarSystemItemBookmarks,
	    UITabBarSystemItemSearch,
	    UITabBarSystemItemDownloads,
	    UITabBarSystemItemMostRecent,
	    UITabBarSystemItemMostViewed,
	}
}
