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
import org.cocoa4android.ns.NSError;
import org.cocoa4android.ns.NSURLRequest;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class UIWebView extends UIView {
	private WebView webView = null;
	private WebViewSizeChangeLisener webViewSizeChangeLisener = null;
	
	private boolean enableMutipleScroll = NO;
	
	private UIWebViewDelegate delegate;
	
	
	public UIWebViewDelegate delegate() {
		return delegate;
	}
	public void setDelegate(UIWebViewDelegate delegate) {
		this.delegate = delegate;
	}
	public boolean isEnableMutipleScroll() {
		return enableMutipleScroll;
	}
	public void setEnableMutipleScroll(boolean enableMutipleScroll) {
		this.enableMutipleScroll = enableMutipleScroll;
	}
	public WebViewSizeChangeLisener getWebViewSizeChangeLisener() {
		return webViewSizeChangeLisener;
	}
	public void setWebViewSizeChangeLisener(
			WebViewSizeChangeLisener webViewSizeChangeLisener) {
		this.webViewSizeChangeLisener = webViewSizeChangeLisener;
	}
	public UIWebView(){
		webView = new ResizableWebView(context);
		webView.getSettings().setBuiltInZoomControls(true);
		CocoaWebViewClient client = new CocoaWebViewClient();
		webView.setWebViewClient(client);
		this.setView(webView);
		
		
	}
	public UIWebView(CGRect frame){
		this();
		this.setFrame(frame);
	}
	public void setFrame(CGRect frame){
		this.frame = frame;
		LayoutParams params = new LayoutParams((int)(frame.size().width()*scaleDensityX), LayoutParams.WRAP_CONTENT);
		params.alignWithParent = true;
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		params.leftMargin = (int)(frame.origin().x()*scaleDensityX);
		params.topMargin = (int)(frame.origin().y()*scaleDensityY);
		this.getView().setLayoutParams(params);
	}
	public void loadHTMLString(String string,String baseUrl){
		webView.loadDataWithBaseURL(baseUrl, string, "text/html", "utf-8", null);
	}
	public void loadRequest(NSURLRequest request){
		webView.loadUrl(request.URL().absoluteString());
	}
	public class ResizableWebView extends WebView{
		public ResizableWebView(Context context) {
			super(context);
		}
		@Override
		protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld){
			super.onSizeChanged(xNew, yNew, xOld, yOld);
			if(webViewSizeChangeLisener!=null){
				webViewSizeChangeLisener.webViewDidChangeSize(xNew/scaleDensityX, yNew/scaleDensityY);
			}
			
		}
	}
	public interface WebViewSizeChangeLisener{
		public void webViewDidChangeSize(float width,float height);
	}
	
	
	public interface UIWebViewDelegate{
		public void webViewDidFinishLoad(UIWebView webView);
		public void webViewDidStartLoad(UIWebView webView);
		public void webViewDidFailLoadWithError(UIWebView webView,NSError error);
	}
	
	public class CocoaWebViewClient extends WebViewClient {
		  @Override
		  public void onPageStarted(WebView view, String url, Bitmap favicon) {
		     if (UIWebView.this.delegate!=null) {
		    	 UIWebView.this.delegate.webViewDidStartLoad(UIWebView.this);
		     }
		  }
		  @Override
		  public void onPageFinished(WebView webView, String url) {
			  if (UIWebView.this.delegate!=null) {
			    	 UIWebView.this.delegate.webViewDidFinishLoad(UIWebView.this);
			  }
		  }
		  @Override
		  public void onReceivedError(WebView view, int errorCode,
		            String description, String failingUrl) {
			  if (UIWebView.this.delegate!=null) {
				  //FIXME nserror description
			    	 UIWebView.this.delegate.webViewDidFailLoadWithError(UIWebView.this,NSError.errorWithDomain("", errorCode,null));
			  }
		   }
		  
		}
	
}
