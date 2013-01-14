/*
 * Copyright (C) 2012 Wu Tong,chao
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
package org.cocoa4android.ns;

import org.cocoa4android.cg.CGAffineTransform;
import org.cocoa4android.cg.CGPoint;
import org.cocoa4android.cg.CGRect;
import org.cocoa4android.cg.CGSize;
import org.cocoa4android.ui.UIApplication;
import org.cocoa4android.util.sbjson.SBJsonWriter;

import android.util.Log;

public class NSObject {
	protected static final boolean YES = true;
	protected static final boolean NO = false;
	
	protected static final int NSNotFound = -1;
	protected static final double M_PI = 3.14159265358979323846264338327950288;
	protected static final double M_PI_2 = 1.57079632679489661923132169163975144;
	protected static final double M_PI_4 = 0.785398163397448309615660845819875721;
	
	public static final String NSDefaultRunLoopMode = "default";
	public static final String NSRunLoopCommonModes = "common";
	
	protected static void NSLog(String format,Object...args){
		Log.i("Cocoa4Android",NSString.stringWithFormat(format, args));
	}
	
	public boolean isEqual(NSObject o) {
		return super.equals(o);
	}
	public boolean isKindOfClass(Class<? extends NSObject> cls) {
		return cls.isInstance(this);
	}
	public int hash() {
		return super.hashCode();
	}
	public String description() {
		return this.toString();
	}
	public String JSONRepresentation(){
		return SBJsonWriter.stringWithObject(this);
	}
	public boolean isArray(){
		return NSArray.class.isInstance(this);
	}
	//================================================================================
    // CG Method
    //================================================================================
	protected CGRect CGRectMake(float x,float y,float width,float height){
		return new CGRect(x,y,width,height);
	}
	protected CGSize CGSizeMake(float width,float height){
		return new CGSize(width, height);
	}
	protected CGPoint CGPointMake(float x,float y){
		return new CGPoint(x, y);
	}
	protected CGAffineTransform CGAffineTransformMake(float a, float b,
			float c, float d, float tx, float ty){
		return new CGAffineTransform(a,b,c,d,tx,ty);
	}
	protected CGAffineTransform CGAffineTransformMakeTranslation(float tx,float ty){
		return new CGAffineTransform(tx,ty);
	}
	protected CGAffineTransform CGAffineTransformMakeScale(float sx,float sy){
		CGAffineTransform transform = new CGAffineTransform();
		transform.a = sx;
		transform.d = sy;
		return transform;
	}
	protected CGAffineTransform CGAffineTransformMakeRotation(float angle) {
		CGAffineTransform transform = new CGAffineTransform();
		
		transform.a = (float) Math.cos(angle);
		transform.b = (float) Math.sin(angle);
		transform.c = (float) -Math.sin(angle);
		transform.d = (float) Math.cos(angle);
		return transform;
	}
	
	protected boolean CGRectContainsPoint(CGRect rect,CGPoint point){
		boolean xIn = point.x>=rect.origin.x&&point.x<=rect.origin.x+rect.size.width;
		boolean yIn = point.y>=rect.origin.y&&point.y<=rect.origin.y+rect.size.height;
		return xIn&&yIn;
	}
	protected boolean CGRectContainsRect(CGRect rect1,CGRect rect2){
		boolean leftTopIn = CGRectContainsPoint(rect1,rect2.origin);
		CGPoint point = CGPointMake(rect2.origin.x+rect2.size.width, rect2.origin.y+rect2.size.height);
		boolean rightBottomIn = CGRectContainsPoint(rect1,point);
		return leftTopIn&&rightBottomIn;
	}
	protected boolean CGRectIntersectsRect(CGRect rect1,CGRect rect2) {
		float minx1 = rect1.origin.x;
		float miny1 = rect1.origin.y;
		float maxx1 = rect1.origin.x+rect1.size.width;
		float maxy1 = rect1.origin.y+rect1.size.height;
		
		float minx2 = rect2.origin.x;
		float miny2 = rect2.origin.y;
		float maxx2 = rect2.origin.x+rect2.size.width;
		float maxy2 = rect2.origin.y+rect2.size.height;
		
		
		float minx   =   Math.max(minx1,   minx2);
		float miny   =   Math.max(miny1,   miny2);
		float maxx   =   Math.min(maxx1,   maxx2);
		float maxy   =   Math.min(maxy1,   maxy2);
		
		
		return minx<=maxx&&miny<=maxy;
	}
	
	
	
	//================================================================================
    // Perform Selector
    //================================================================================
	public void performSelectorInBackground(String aSelector,Object arg){
		NSMethodSignature sig = class2NSClass(this.getClass()).instanceMethodSignatureForSelector(aSelector);
		
		final NSInvocation invocation = NSInvocation.invocationWithMethodSignature(sig);
		invocation.setTarget(this);
		invocation.setArgument(arg, 2);
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				invocation.invoke();
			}
		});
		thread.start();
	}
	public void performSelectorOnMainThread(String aSelector,Object arg,boolean wait){
		NSMethodSignature sig = class2NSClass(this.getClass()).instanceMethodSignatureForSelector(aSelector);
		
		final NSInvocation invocation = NSInvocation.invocationWithMethodSignature(sig);
		invocation.setTarget(this);
		invocation.setArgument(arg, 2);
		UIApplication.sharedApplication().getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				invocation.invoke();
			}
		});
	}
	
	protected NSClass class2NSClass(Class<? extends Object> class1) {
		return new NSClass(class1);
	}
	protected String selector(String selector) {
		return selector;
	}
}
