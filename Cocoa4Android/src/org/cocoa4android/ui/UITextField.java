package org.cocoa4android.ui;

import org.cocoa4android.cg.CGRect;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import org.cocoa4android.ns.NSTextAlignment;


public class UITextField extends UIView {
	private EditText textField = null;
	public UITextField(){
		textField = new EditText(context);
		
		this.setTextField(textField);
		this.setView(textField);
		
		ShapeDrawable background = new ShapeDrawable(new RoundRectShape(new float[] {25,25, 25,25, 25,25, 25,25},null,null));
		background.getPaint().setColor(UIColor.getWhiteColor().getColor());
		background.getPaint().setStrokeWidth(2);
		this.textField.setBackgroundDrawable(background);
		
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
	public String getText(){
		return this.textField.getText().toString();
	}
	public void setTextColor(UIColor color){
		this.textField.setTextColor(color.getColor());
	}
	public void setTextAlignment(NSTextAlignment alignment){
		switch (alignment) {
		case NSTextAlignmentLeft:
			this.textField.setGravity(Gravity.LEFT);
			break;
		case NSTextAlignmentCenter:
			this.textField.setGravity(Gravity.CENTER);
			break;
		case NSTextAlignmentRight:
			this.textField.setGravity(Gravity.RIGHT);
			break;
		}
		
	}
	public void setFontSize(float fontSize){
		this.textField.setTextSize(fontSize);
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
		textField.requestFocus();
		
		InputMethodManager imm = (InputMethodManager) UIApplication.getSharedApplication().getDelegate().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(textField, InputMethodManager.SHOW_IMPLICIT);
		
		//UIApplication.getSharedApplication().getDelegate().getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
	}
	public void resignFirstResponder(){
		textField.clearFocus();
		
		InputMethodManager imm = (InputMethodManager) UIApplication.getSharedApplication().getDelegate().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(textField.getWindowToken(), 0);
	}
	
}
