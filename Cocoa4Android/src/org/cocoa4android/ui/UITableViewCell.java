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
	
	private ShapeDrawable background = null;
	
	
	public UITableViewCell() {
		super();
		AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		
		this.getView().setLayoutParams(params);
		
		background = new ShapeDrawable(UITableViewCellShape.UITableViewCellShapeNoRound);
		background.getPaint().setColor(UIColor.getWhiteColor().getColor());
		this.getView().setBackgroundDrawable(background);
	}
	
	public UITableViewCell(UITableViewCellStyle style) {
		super();
		
		switch(style){
			case UITableViewCellStyleDefault:initDefaultTableViewCell();break;
			case UITableViewCellStyleValue1:initValue1TableViewCell();break;
			case UITableViewCellStyleValue2:initValue2TableViewCell();break;
			case UITableViewCellStyleSubtitle:initSubtitleViewCell();break;
		}
		AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.FILL_PARENT, (int)(44*density));
		this.getView().setLayoutParams(params);
		background = new ShapeDrawable(UITableViewCellShape.UITableViewCellShapeNoRound);
		background.getPaint().setColor(UIColor.getWhiteColor().getColor());
		this.getView().setBackgroundDrawable(background);
	}
	@Override
	public void setFrame(CGRect frame) {
		AbsListView.LayoutParams params = new AbsListView.LayoutParams(
				(int)(frame.getSize().getWidth()*density), 
				(int)(frame.getSize().getHeight()*density)
			);
		this.getView().setLayoutParams(params);
	}
	public void setBackgroundColor(UIColor color) {
		background.getPaint().setColor(color.getColor());
	}
	public UIColor getBackgroundColor() {
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
		
		imageView = new UIImageView();
		this.addSubView(imageView);
		
		textLabel = new UILabel();
		this.addSubView(textLabel);
		
		detailTextLabel = new UILabel();
		this.addSubView(detailTextLabel);
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
		AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.FILL_PARENT, (int)(height*density));
		this.getView().setLayoutParams(params);
	}
	
	public enum UITableViewCellStyle {
		UITableViewCellStyleDefault,	// Simple cell with text label and optional image view (behavior of UITableViewCell in iPhoneOS 2.x)
	    UITableViewCellStyleValue1,		// Left aligned label on left and right aligned label on right with blue text (Used in Settings)
	    UITableViewCellStyleValue2,		// Right aligned label on left with blue text and left aligned label on right (Used in Phone/Contacts)
	    UITableViewCellStyleSubtitle	// Left aligned label on top and left aligned label on bottom with gray text (Used in iPod).
	}
	
	public enum UITableViewCellSeparatorStyle {
		UITableViewCellSeparatorStyleNone,
	    UITableViewCellSeparatorStyleSingleLine,
	    UITableViewCellSeparatorStyleSingleLineEtched   // This separator style is only supported for grouped style table views currently
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
	
}
