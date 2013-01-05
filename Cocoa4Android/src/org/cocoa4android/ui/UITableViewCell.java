/*
 * Copyright (C) 2012 chao,Wu Tong
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

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.widget.AbsListView;
import android.widget.RelativeLayout.LayoutParams;


public class UITableViewCell extends UIView {
	private UILabel textLabel;
	private UILabel detailTextLabel;
	private UIImageView imageView;
	private String reuseIdentifier;
	
	
	public String reuseIdentifier() {
		return reuseIdentifier;
	}

	public void setReuseIdentifier(String reuseIdentifier) {
		this.reuseIdentifier = reuseIdentifier;
	}

	private ShapeDrawable background = null;
	
	
	public UITableViewCell() {
		super();
		AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		
		this.getView().setLayoutParams(params);
		
		background = new ShapeDrawable(UITableViewCellShape.UITableViewCellShapeNoRound);
		background.getPaint().setColor(UIColor.whiteColor().getColor());
		this.getView().setBackgroundDrawable(background);
	}

	public UITableViewCell(UITableViewCellStyle style,String reuseIdentifier) {
		super();
		this.setReuseIdentifier(reuseIdentifier);
		switch(style){
			case UITableViewCellStyleDefault:initDefaultTableViewCell();break;
			case UITableViewCellStyleValue1:initValue1TableViewCell();break;
			case UITableViewCellStyleValue2:initValue2TableViewCell();break;
			case UITableViewCellStyleSubtitle:initSubtitleViewCell();break;
		}
		AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.FILL_PARENT, (int)(44*scaleDensityY));
		this.getView().setLayoutParams(params);
		background = new ShapeDrawable(UITableViewCellShape.UITableViewCellShapeNoRound);
		background.getPaint().setColor(UIColor.whiteColor().getColor());
		this.getView().setBackgroundDrawable(background);
	}
	
	@Override
	public void setFrame(CGRect frame) {
		AbsListView.LayoutParams params = new AbsListView.LayoutParams(
				(int)(frame.size().width()*scaleDensityX), 
				(int)(frame.size().height()*scaleDensityY)
			);
		this.getView().setLayoutParams(params);
	}
	
	@Override
	public void addSubview(UIView child) {
		super.addSubview(child);
		if (UIWebView.class.isInstance(child)) {
			((UIWebView)child).setEnableMutipleScroll(YES);
		}else if(UITableView.class.isInstance(child)){
			((UITableView)child).setEnableMutipleScroll(YES);
		}
	}
	
	public void setBackgroundColor(UIColor color) {
		background.getPaint().setColor(color.getColor());
	}
	public UIColor backgroundColor() {
		return new UIColor(background.getPaint().getColor());
	}
	public void setShapeType(UITableViewCellShapeType shapeType) {
		switch(shapeType) {
		case UITableViewCellShapeNoRound:
			background.setShape(UITableViewCellShape.UITableViewCellShapeNoRound);
			break;
		case UITableViewCellShapeAllRound:
			background.setShape(UITableViewCellShape.UITableViewCellShapeAllRound);
			break;
		case UITableViewCellShapeTopRound:
			background.setShape(UITableViewCellShape.UITableViewCellShapeTopRound);
			break;
		case UITableViewCellShapeBottomRound:
			background.setShape(UITableViewCellShape.UITableViewCellShapeBottomRound);
			break;
		}
	}
	private void initDefaultTableViewCell(){
	}
	private void initValue1TableViewCell(){
		
	}
	private void initValue2TableViewCell(){
		
	}
	private void initSubtitleViewCell(){
		
	}
	public UILabel getTextLabel() {
		return textLabel;
	}
	public UILabel getDetailTextLabel() {
		return detailTextLabel;
	}
	public UIImageView getImageView() {
		return imageView;
	}
	
	public void setHeight(float height) {
		AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.FILL_PARENT, (int)(height*scaleDensityY));
		this.getView().setLayoutParams(params);
	}
	
	public enum UITableViewCellStyle {
		UITableViewCellStyleDefault,	// Simple cell with text label and optional image view (behavior of UITableViewCell in iPhoneOS 2.x)
	    UITableViewCellStyleValue1,		// Left aligned label on left and right aligned label on right with blue text (Used in Settings)
	    UITableViewCellStyleValue2,		// Right aligned label on left with blue text and left aligned label on right (Used in Phone/Contacts)
	    UITableViewCellStyleSubtitle	// Left aligned label on top and left aligned label on bottom with gray text (Used in iPod).
	}
	

	
	private interface UITableViewCellShape {
		final RoundRectShape UITableViewCellShapeNoRound = new RoundRectShape(new float[] {0,0, 0,0, 0,0, 0,0},null,null);
		final RoundRectShape UITableViewCellShapeTopRound = new RoundRectShape(new float[] {10,10, 10,10, 0,0, 0,0},null,null);
		final RoundRectShape UITableViewCellShapeBottomRound = new RoundRectShape(new float[] {0,0, 0,0, 10,10, 10,10},null,null);
		final RoundRectShape UITableViewCellShapeAllRound = new RoundRectShape(new float[] {10,10, 10,10, 10,10, 10,10},null,null);
	}
	
	public enum UITableViewCellShapeType {
		UITableViewCellShapeNoRound,
		UITableViewCellShapeTopRound,
		UITableViewCellShapeBottomRound,
		UITableViewCellShapeAllRound
	}
	
	private UITableViewCellSelectionStyle selectionStyle = UITableViewCellSelectionStyle.UITableViewCellSelectionStyleBlue;
	
	
	public UITableViewCellSelectionStyle selectionStyle() {
		return selectionStyle;
	}

	public void setSelectionStyle(UITableViewCellSelectionStyle selectionStyle) {
		this.selectionStyle = selectionStyle;
	}

	public enum UITableViewCellSelectionStyle{
		UITableViewCellSelectionStyleNone,
	    UITableViewCellSelectionStyleBlue,
	    UITableViewCellSelectionStyleGray
	}
	
}
