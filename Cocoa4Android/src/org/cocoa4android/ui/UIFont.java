package org.cocoa4android.ui;

import org.cocoa4android.ns.NSObject;

import android.graphics.Typeface;

public class UIFont extends NSObject {

	protected Typeface font;
	protected float fontSize;
	
	public UIFont() {
		font = Typeface.DEFAULT;
	}
	
	public UIFont(String fontName,float fontSize) {
		this.fontSize = fontSize;
		font = Typeface.create(fontName, Typeface.NORMAL);
	}
	
	public UIFont(String fontName) {
		this(fontName, 21);
	}

	public static UIFont fontWithName(String fontName,float fontSize) {
		return new UIFont(fontName, fontSize);
	}
	
	public Typeface getFont() {
		return font;
	}
	
	public float fontSize() {
		return this.fontSize;
	}
}
