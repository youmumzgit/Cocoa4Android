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
import org.cocoa4android.ns.NSString;

import android.content.Context;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class UIWebView extends UIView {
	private WebView webView = null;
	private WebViewSizeChangeLisener webViewSizeChangeLisener = null;
	public WebViewSizeChangeLisener getWebViewSizeChangeLisener() {
		return webViewSizeChangeLisener;
	}
	public void setWebViewSizeChangeLisener(
			WebViewSizeChangeLisener webViewSizeChangeLisener) {
		this.webViewSizeChangeLisener = webViewSizeChangeLisener;
	}
	public UIWebView(){
		webView = new ResizableWebView(context);
		
		this.setView(webView);
		
	}
	public UIWebView(CGRect frame){
		this();
		this.setFrame(frame);
	}
	public void setFrame(CGRect frame){
		this.frame = frame;
		LayoutParams params = new LayoutParams((int)(frame.size().width()*density), LayoutParams.WRAP_CONTENT);
		//LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		params.alignWithParent = true;
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		params.leftMargin = (int)(frame.origin().x()*density);
		params.topMargin = (int)(frame.origin().y()*density);
		this.getView().setLayoutParams(params);
	}
	public void loadHTMLString(String string,String baseUrl){
		webView.loadDataWithBaseURL(baseUrl, string, "text/html", "utf-8", null);
	}
	public class ResizableWebView extends WebView{

		public ResizableWebView(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}
		@Override
		protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld){
			super.onSizeChanged(xNew, yNew, xOld, yOld);
			if(webViewSizeChangeLisener!=null){
				webViewSizeChangeLisener.webViewDidChangeSize(xNew/density, yNew/density);
			}
		}
	}
	public interface WebViewSizeChangeLisener{
		public void webViewDidChangeSize(float width,float height);
	}
}
