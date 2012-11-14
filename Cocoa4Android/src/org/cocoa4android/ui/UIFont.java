package org.cocoa4android.ui;

import org.cocoa4android.ns.NSObject;
import org.cocoa4android.ns.NSString;

import android.graphics.Typeface;

public class UIFont extends NSObject {

	protected Typeface font;
	protected float fontSize;
	
	public UIFont() {
		font = Typeface.DEFAULT;
	}
	
	public UIFont(NSString fontName,float fontSize) {
		this.fontSize = fontSize;
		font = Typeface.create(fontName.getString(), Typeface.NORMAL);
	}
	
	public UIFont(NSString fontName) {
		this(fontName, 21);
	}

	public static UIFont fontWithName(NSString fontName,float fontSize) {
		return new UIFont(fontName, fontSize);
	}
	
	public Typeface getFont() {
		return font;
	}
	
	public float fontSize() {
		return this.fontSize;
	}
}
