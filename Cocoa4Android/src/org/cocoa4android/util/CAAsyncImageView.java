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
package org.cocoa4android.util;


import org.cocoa4android.cg.CGRect;
import org.cocoa4android.extend.urlimage.UrlImageViewCallback;
import org.cocoa4android.extend.urlimage.UrlImageViewHelper;
import org.cocoa4android.ui.UIImage;
import org.cocoa4android.ui.UIImageView;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class CAAsyncImageView extends UIImageView implements UrlImageViewCallback{
	private CAAsyncImageViewCallback imageViewCallback;
	public CAAsyncImageView(UIImage image){
		super(image);
	}
	public CAAsyncImageView(){
		super();
	}
	public CAAsyncImageView(CGRect frame){
		super(frame);
	}
	public void setImageUrl(String imageUrl){
		if(this.image()!=null){
			UrlImageViewHelper.setUrlDrawable(this.imageView, imageUrl, this.image().getDrawable(),this);
		}else{
			UrlImageViewHelper.setUrlDrawable(this.imageView, imageUrl,this);
		}
	}
	@Override
	public void onLoaded(ImageView imageView, Drawable loadedDrawable,
			String url, boolean loadedFromCache) {
		// TODO Auto-generated method stub
		if(imageViewCallback!=null){
			imageViewCallback.imageDidLoaded(this);
		}
	}
	public CAAsyncImageViewCallback getImageViewCallback() {
		return imageViewCallback;
	}
	public void setImageViewCallback(CAAsyncImageViewCallback imageViewCallback) {
		this.imageViewCallback = imageViewCallback;
	}
	public interface CAAsyncImageViewCallback{
		public void imageDidLoaded(CAAsyncImageView imageView);
	}
	
}
