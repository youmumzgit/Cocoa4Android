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

import org.cocoa4android.ns.NSObject;

import android.graphics.Typeface;

public class UIFont extends NSObject {

	protected Typeface font;
	protected float fontSize;
	
	public UIFont() {
		font = Typeface.DEFAULT;
	}
	public UIFont(float fontSize) {
		this();
		this.fontSize = fontSize;
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
	public static UIFont systemFontOfSize(float fontSize){
		return new UIFont(fontSize);
	}
	public static UIFont boldSystemFontOfSize(float fontSize){
		UIFont f = new UIFont(fontSize);
		f.font = Typeface.DEFAULT_BOLD;
		return f;
	}
	public static UIFont italicSystemFontOfSize(float fontSize){
		UIFont f = new UIFont(fontSize);
		f.font = Typeface.defaultFromStyle(Typeface.ITALIC);
		return f;
	}
	public static UIFont fontWithSize(float fontSize){
		UIFont f = new UIFont(fontSize);
		return f;
	}
	public Typeface getFont() {
		return font;
	}
	
	public float fontSize() {
		return this.fontSize;
	}
}
