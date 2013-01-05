package org.cocoa4android.ui;

import org.cocoa4android.R;
import org.cocoa4android.cg.CGRect;
import org.cocoa4android.ns.NSArray;
import org.cocoa4android.ns.NSMutableArray;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView.ScaleType;

public class UITabBar extends UIView {
	
	UITabBarController tabBarController = null;
	
	private float tabBarHeight;
	public UITabBar() {
		CGRect appFrame = UIScreen.mainScreen().applicationFrame();
		tabBarHeight = appFrame.size.height/10.0f;
		Bitmap bitmap = BitmapFactory.decodeResource(UIApplication.sharedApplication().getActivity().getResources(), R.drawable.zz_c4a_tabbar_background);  
		BitmapDrawable bd = new BitmapDrawable(bitmap);  
		bd.setTileModeX(TileMode.REPEAT);
		bd.setDither(true);
		this.setBackgroundImage(new UIImage(bd.getBitmap()));
		this.setFrame(CGRectMake(0,appFrame.size().height()-tabBarHeight,appFrame.size().width(),tabBarHeight));
	}
	private NSMutableArray items;
	public NSArray items() {
		return items;
	}
	public void setItems(NSMutableArray items) {
		this.items = items;
		if (items!=null) {
			int count = items.count();
			int width = (int) (this.frame.size.width/count);
			int halfWidth = width>>1;
			
			for (int i = 0; i < count; i++) {
				UITabBarItem item = (UITabBarItem) items.objectAtIndex(i);
				item.tabBar = this;
				item.itemIndex = i;
				UIButton itemButton = item.itemButton;
				int y = ((int)tabBarHeight)>>1;
				int x = (i*2+1)*halfWidth;
				itemButton.setFrame(CGRectMake(0, 0, width, tabBarHeight));
				itemButton.setCenter(CGPointMake(x, y));
				itemButton.imageView().setFrame(CGRectMake(0, 0, halfWidth, y));
				itemButton.imageView().setCenter(CGPointMake(halfWidth, (y>>1)+2*UIView.scaleDensityY));
				itemButton.imageView().getImageView().setScaleType(ScaleType.CENTER_INSIDE);
				this.addSubview(itemButton);
			}
		}
		
	}
	void setItemAtIndex(UITabBarItem tabBarItem,int index){
		UITabBarItem item = (UITabBarItem) items.objectAtIndex(index);
		tabBarItem.tabBar = this;
		tabBarItem.itemIndex = item.itemIndex;
		tabBarItem.itemButton.setFrame(item.itemButton.frame());
		if (item==selectedItem) {
			tabBarItem.select();
		}
		this.addSubview(item.itemButton);
		item.itemButton.removeFromSuperView();
		items.replaceObject(index, tabBarItem);
	}
	private UITabBarItem selectedItem;
	public UITabBarItem selectedItem() {
		return selectedItem;
	}
	public void setSelectedItem(UITabBarItem selectedItem) {
		this.selectedItem = selectedItem;
	}
}
