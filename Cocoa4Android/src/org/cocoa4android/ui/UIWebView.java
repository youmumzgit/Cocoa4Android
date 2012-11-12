package org.cocoa4android.ui;

import org.cocoa4android.cg.CGRect;

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
		LayoutParams params = new LayoutParams((int)(frame.getSize().getWidth()*density), LayoutParams.WRAP_CONTENT);
		//LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		params.alignWithParent = true;
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		params.leftMargin = (int)(frame.getOrigin().getX()*density);
		params.topMargin = (int)(frame.getOrigin().getY()*density);
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
