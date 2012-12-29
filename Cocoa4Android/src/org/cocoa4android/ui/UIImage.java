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

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;

public class UIImage {
	private static SparseArray<UIImage> highlightImages = new SparseArray<UIImage>();
	
	public static UIImage imageNamed(int resId){
		return new UIImage(resId);
	}
	
	private Drawable drawable;
	private int resId=0;
	
	
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
	}
	public Drawable getDrawable() {
		if (drawable==null&&resId!=0) {
			drawable = UIApplication.sharedApplication().delegate().getResources().getDrawable(resId);
		}
		return drawable;
	}
	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
	}
		
	UIImage createHighlightImage(){
		if (resId>0) {
			UIImage highlightImage = highlightImages.get(resId);
			if (highlightImage!=null) {
				return highlightImage;
			}
		}
		if(BitmapDrawable.class.isInstance(getDrawable())){
			Bitmap bitmap = ((BitmapDrawable)this.getDrawable()).getBitmap();
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			int[] argb = new int[width*height];
			bitmap.getPixels(argb, 0, width, 0, 0, width, height);
			for (int i = 0; i < argb.length; i++) {
				int alpha = Color.alpha(argb[i]);
				int red = Color.red(argb[i]);
				int green = Color.green(argb[i]);
				int blue = Color.blue(argb[i]);
				if (alpha > 0) {
					argb[i] = Color.argb(alpha, red>>1, green>>1, blue>>1);
				}
			}
			UIImage highlightImage = new UIImage(Bitmap.createBitmap(argb, width, height, Config.ARGB_4444));
			if (resId>0) {
				highlightImages.put(resId, highlightImage);
			}
			return highlightImage;
		}
		return null;
		
		
	}
	
}
