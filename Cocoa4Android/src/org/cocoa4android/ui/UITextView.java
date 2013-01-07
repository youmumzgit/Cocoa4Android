package org.cocoa4android.ui;

import org.cocoa4android.ns.NSTextAlignment;

import android.content.Context;
import android.view.Gravity;
import android.widget.EditText;

public class UITextView extends UIView {
	
	private DisableEditText editText = null;
	public UITextView(){
		editText = new DisableEditText(context);
		editText.setGravity(Gravity.CENTER_VERTICAL);
		this.setView(editText);
		this.setBackgroundColor(UIColor.clearColor());
	}
	public void setText(String text){
		this.editText.setText(text);
	}

	public String text(){
		return this.editText.getText().toString();
	}
	private UIColor textColor;
	public void setTextColor(UIColor color){
		this.editText.setTextColor(color.getColor());
		textColor = color;
	}
	public UIColor textColor(){
		return textColor;
	}
	private NSTextAlignment textAlignment;
	public void setTextAlignment(NSTextAlignment alignment){
		switch (alignment) {
		case NSTextAlignmentLeft:
			this.editText.setGravity(editText.getGravity()&Gravity.VERTICAL_GRAVITY_MASK|Gravity.LEFT);
			break;
		case NSTextAlignmentCenter:
			this.editText.setGravity(editText.getGravity()&Gravity.VERTICAL_GRAVITY_MASK|Gravity.CENTER);
			break;
		case NSTextAlignmentRight:
			this.editText.setGravity(editText.getGravity()&Gravity.VERTICAL_GRAVITY_MASK|Gravity.RIGHT);
			break;
		}
		textAlignment = alignment;
	}
	public NSTextAlignment textAlignment() {
		return textAlignment;
	}
	private boolean editable;
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
		this.editText.setEditable(editable);
	}
	public void setFont(UIFont font) {
		this.setFontSize(font.fontSize);
		this.editText.setTypeface(font.getFont());
	}
	public void setFontSize(float fontSize){
		this.editText.setTextSize(fontSize*UIScreen.mainScreen().getDensityText());
	}
	public boolean hasText(){
		return editText.getText().length()>0;
	}
	
	public class DisableEditText extends EditText{
		private boolean editable = true;
		public DisableEditText(Context context) {
			super(context);
		}
		public boolean isEditable() {
			return editable;
		}
		public void setEditable(boolean editable) {
			this.editable = editable;
		}
		@Override      
		public boolean onCheckIsTextEditor() {   
			return editable&&super.onCheckIsTextEditor();     
		}   
		
	}
}
