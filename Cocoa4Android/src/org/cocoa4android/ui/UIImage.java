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

import org.cocoa4android.cg.CGSize;
import org.cocoa4android.ns.NSObject;

import android.R.integer;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;

public class UIImage extends NSObject{
	private static SparseArray<UIImage> highlightImages = new SparseArray<UIImage>();
	
	private static SparseArray<UIImage> tabBarImages = new SparseArray<UIImage>();
	private static SparseArray<UIImage> tabBarHighlightImages = new SparseArray<UIImage>();
	
	public static UIImage imageNamed(int resId){
		return new UIImage(resId);
	}
	
	private Drawable drawable;
	private int resId=0;
	private CGSize size;
	
	public CGSize size() {
		return size;
	}
	public UIImage(int resId){
		this.setResId(resId);
	}
	public UIImage(Drawable drawable){
		this.setDrawable(drawable);
	}
	public UIImage(Bitmap bitmap){
		Drawable drawable = new BitmapDrawable(bitmap);
		this.setDrawable(drawable);
	}

	protected int getResId() {
		return resId;
	}


	public void setResId(int resId) {
		this.resId = resId;
		if (drawable==null&&resId!=0) {
			this.setDrawable(UIApplication.sharedApplication().getActivity().getResources().getDrawable(resId));
		}
	}
	private Bitmap bitmap = null;
	public Drawable getDrawable() {
		return drawable;
	}
	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
		if(BitmapDrawable.class.isInstance(getDrawable())){
			this.bitmap = ((BitmapDrawable)this.getDrawable()).getBitmap();
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			this.size = CGSizeMake(width, height);
		}
	}
		
	UIImage createHighlightImage(){
		if (resId>0) {
			UIImage highlightImage = highlightImages.get(resId);
			if (highlightImage!=null) {
				return highlightImage;
			}
		}
		if(bitmap!=null){
			int[] argb = new int[(int)size.width*(int)size.height];
			bitmap.getPixels(argb, 0, (int)size.width, 0, 0, (int)size.width, (int)size.height);
			for (int i = 0; i < argb.length; i++) {
				int alpha = Color.alpha(argb[i]);
				int red = Color.red(argb[i]);
				int green = Color.green(argb[i]);
				int blue = Color.blue(argb[i]);
				if (alpha > 40) {
					argb[i] = Color.argb(alpha, red>>1, green>>1, blue>>1);
				}
			}
			UIImage highlightImage = new UIImage(Bitmap.createBitmap(argb, (int)size.width, (int)size.height, Config.ARGB_4444));
			if (resId>0) {
				highlightImages.put(resId, highlightImage);
			}
			return highlightImage;
		}
		return null;
		
		
	}
	UIImage createTabBarImage(boolean highlighted){
		if (resId>0) {
			UIImage tabBarImage = null;;
			if (highlighted) {
				tabBarImage = tabBarHighlightImages.get(resId);
			}else{
				tabBarImage = tabBarImages.get(resId);
			}
			if (tabBarImage!=null) {
				return tabBarImage;
			}
		}
		if(bitmap!=null){
			UIImage tabBarImage = null;
			int w = (int)size.width;
			int h = (int)size.height;
			int[] argb = new int[w*h];
			bitmap.getPixels(argb, 0, w, 0, 0, w, h);
			//FIXME no shadow effect
			
			if (highlighted) {
				int index = 0;
				int lift = h>>3;
				
				int level1 = (w+h)/200+1;
				int level2 = (w+h)/25+1;
				
				for (int i = 0; i < h; i++) {
					for (int j = 0; j < w; j++) {
						int row = h-i;
						int alpha = Color.alpha(argb[index]);
						if (alpha > 0) {
							int delta = 0;
							if ((row-lift)>j>>1) {
								delta = (i+j)/level1;
								//130 184 238    80  40  10
								argb[index] = Color.argb(255, 226-delta, 232-(delta>>1), 250-(delta>>3));
							}else{
								delta = (i+j)/level2;
								//15 190 245    15   45     15
								argb[index] = Color.argb(255, delta, 145+delta, 230+delta);
							}
							
						}
						index++;
					}
				}
				
			}else{
				int index = 0;
				//90~160
				int level = h/75+1;
				
				for (int i = 0; i < h; i++) {
					for (int j = 0; j < w; j++) {
						int alpha = Color.alpha(argb[index]);
						int delta = 0;
						if (level>0) {
							delta = i/level;
						}
						if (alpha > 0) {
							argb[index] = Color.argb(255, 160-delta, 160-delta, 160-delta);
						}
						index++;
					}
				}
			}
			
			tabBarImage = new UIImage(Bitmap.createBitmap(argb, w, h, Config.ARGB_4444));
			if (resId>0) {
				if (highlighted) {
					tabBarHighlightImages.put(resId, tabBarImage);
				}else{
					tabBarImages.put(resId, tabBarImage);
				}
			}
			return tabBarImage;
		}
		return null;
	}
}
