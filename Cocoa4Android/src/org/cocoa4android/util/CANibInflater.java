package org.cocoa4android.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.cocoa4android.ui.UIApplication;
import org.cocoa4android.ui.UIButton;
import org.cocoa4android.ui.UIImageView;
import org.cocoa4android.ui.UIView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.res.AssetManager;
import android.util.Xml;


public class CANibInflater {
	public static UIView[] inflater(String xib,Object fileOwner){
		AssetManager assetManager = UIApplication.getSharedApplication().getDelegate().getResources().getAssets();
		InputStream is;
		try {
			is = assetManager.open(xib);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
		    DocumentBuilder builder = factory.newDocumentBuilder(); 
		    Document document = builder.parse(is); 
		    Element element = document.getDocumentElement(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
		return null;
	}
}
	/*
	public static UIView[] inflater(String xib,Object fileOwner){
		AssetManager assetManager = UIApplication.getSharedApplication().getDelegate().getResources().getAssets();
		InputStream is;
		ArrayList<UIView> rootOfView = null;
		try {
			is = assetManager.open(xib);
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(is,"utf-8");
			
			
			Stack<DepthView> viewStack = new Stack<DepthView>();
			
			DepthView currentView = null;
					
			int rootDepth = 0;
			
			for (int event = parser.getEventType(); event != XmlPullParser.END_DOCUMENT; event = parser.next()) {
				if(event==XmlPullParser.START_TAG){
					//start of a tag
					String key = parser.getAttributeValue(null, "key");
					String ibclass = parser.getAttributeValue(null, "class");
					int depth =  parser.getDepth();
					if(key.equals("IBDocument.RootObjects")){
						//start
						rootOfView = new ArrayList<UIView>(3);
						rootDepth = depth;
					}
					if(rootDepth!=0){
						if(key.equals("NSSubviews")){
							viewStack.push(currentView);
						}else{
							if(ibclass.equals("IBUIView")){
								currentView = new DepthView(new UIView(),depth);
							}else if(ibclass.equals("IBUIButton")){
								currentView = new DepthView(new UIButton(),depth);
							}else if(ibclass.equals("IBUIImageView")){
								currentView =  new DepthView(new UIImageView(),depth);
							}
							
							if(key.equals("NSFrame")){
								
							}
						}
					}
				}else if(event==XmlPullParser.END_TAG){
					if(rootDepth!=0){
						int dep = parser.getDepth();
						if(dep==rootDepth){
							break;
						}
					}
				}
	          
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(rootOfView!=null){
			return (UIView[]) rootOfView.toArray();
		}else{
			return null;
		}
	}
}
class DepthView{
	public DepthView(UIView view,int depth){
		this.view = view;
		this.depth = depth;
	}
	public UIView view;
	public int depth;
}*/
