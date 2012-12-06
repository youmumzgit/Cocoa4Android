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

import android.content.Context;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import org.cocoa4android.ns.NSTextAlignment;


public class UITextField extends UIView {
	private EditText textField = null;
	public String placeholder;
	private float densityX = UIScreen.mainScreen().getDensityX();
	private float densityY = UIScreen.mainScreen().getDensityY();
	
	public UITextField(){
		textField = new EditText(context);
		textField.setFocusable(YES);
		this.setTextField(textField);
		this.setView(textField);
		
		ShapeDrawable background = new ShapeDrawable(new RoundRectShape(new float[] {8*densityX,8*densityY, 8*densityX,8*densityY, 8*densityX,8*densityY, 8*densityX,8*densityY},null,null));
		background.getPaint().setColor(UIColor.whiteColor().getColor());
		background.getPaint().setStrokeWidth(1);
		this.textField.setBackgroundDrawable(background);
		this.textField.setPadding((int)(8*densityX), 0, (int)(8*densityX), 0);
	}
	public UITextField(CGRect frame){
		this();
		this.setFrame(frame);
	}
	public EditText getTextField() {
		return textField;
	}
	public void setTextField(EditText textField) {
		this.textField = textField;
	}
	public void setText(String text){
		this.textField.setText(text);
	}

	public String text(){
		return this.textField.getText().toString();
	}
	public void setTextColor(UIColor color){
		this.textField.setTextColor(color.getColor());
	}
	public void setTextAlignment(NSTextAlignment alignment){
		switch (alignment) {
		case NSTextAlignmentLeft:
			this.textField.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
			break;
		case NSTextAlignmentCenter:
			this.textField.setGravity(Gravity.CENTER|Gravity.CENTER_VERTICAL);
			break;
		case NSTextAlignmentRight:
			this.textField.setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
			break;
		}
		
	}
	public void setFrame(CGRect frame){
		super.setFrame(frame);
		//this.textField.settex
	}
	public void setFont(UIFont font) {
		this.setFontSize(font.fontSize);
		this.textField.setTypeface(font.getFont());
	}
	public void setFontSize(float fontSize){
		this.textField.setTextSize(fontSize*UIScreen.mainScreen().getDensityText());
	}
	public void setSecureTextEntry(boolean secureTextEntry){
		if(secureTextEntry){
			String temporary_stored_text = textField.getText().toString().trim();
			textField.setTransformationMethod(PasswordTransformationMethod.getInstance());
			textField.setText(temporary_stored_text);
		}else{
			textField.setTransformationMethod(null);
		}
	}
	public boolean isSecureTextEntry(){
		return false;
	}
	public void becomeFirstResponder(){
		InputMethodManager imm = (InputMethodManager)textField.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		if (!imm.isActive()) {
			imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	public void resignFirstResponder(){
		textField.clearFocus();
		InputMethodManager imm = (InputMethodManager)textField.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive()) {
			imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	
	public String placeholder() {
		return placeholder;
	}
	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
		this.textField.setHintTextColor(UIColor.lightGrayColor().getColor());
		this.textField.setHint(placeholder);
	}
}
