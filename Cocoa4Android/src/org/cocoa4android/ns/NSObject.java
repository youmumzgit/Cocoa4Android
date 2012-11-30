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
import org.cocoa4android.third.sbjson.SBJsonWriter;

import android.util.Log;

public class NSObject {
	public static final boolean YES = true;
	public static final boolean NO = false;
	
	public static final int NSNotFound = -1;
	
	public static void NSLog(String format,Object...args){
		Log.i("Cocoa4Android",NSString.stringWithFormat(format, args).getString());
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
	
	
	
	/*
	 * CG MAKE Method
	 */
	protected CGRect CGRectMake(float x,float y,float width,float height){
		return new CGRect(x,y,width,height);
	}
	protected CGSize CGSizeMake(float width,float height){
		return new CGSize(width, height);
	}
	protected CGPoint CGPoint(float x,float y){
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
}
