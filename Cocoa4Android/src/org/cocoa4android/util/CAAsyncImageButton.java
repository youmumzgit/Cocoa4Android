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
import org.cocoa4android.extend.urlimage.UrlImageButtonCallback;
import org.cocoa4android.extend.urlimage.UrlImageButtonHelper;
import org.cocoa4android.ui.UIButton;

import android.graphics.drawable.Drawable;
import android.widget.Button;

public class CAAsyncImageButton extends UIButton implements UrlImageButtonCallback{
	private CAAsyncImageButtonCallback imageButtonCallback;
	
	public CAAsyncImageButton(){
		super();
	}
	public CAAsyncImageButton(CGRect frame){
		this();
		this.setFrame(frame);
	}
	public void setImageUrl(String imageUrl){
		if(this.getButton().getBackground()!=null){
			UrlImageButtonHelper.setUrlDrawable(this.button, imageUrl, this.button.getBackground(),this);
		}else{
			UrlImageButtonHelper.setUrlDrawable(this.button, imageUrl,this);
		}
	}
	
	public CAAsyncImageButtonCallback getImageButtonCallback() {
		return imageButtonCallback;
	}
	public void setImageButtonCallback(CAAsyncImageButtonCallback imageButtonCallback) {
		this.imageButtonCallback = imageButtonCallback;
	}

	public interface CAAsyncImageButtonCallback{
		public void imageDidLoaded(CAAsyncImageButton imageButton);
	}

	@Override
	public void onLoaded(Button button, Drawable loadedDrawable, String url,
			boolean loadedFromCache) {
		if(imageButtonCallback!=null){
			imageButtonCallback.imageDidLoaded(this);
		}
		
	}
}
