/*
 * Copyright (C) 2012 Yang Chuan
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

public class NSString extends NSObject {
	protected String content = null;
	
	public NSString(){
		this.content = "";
	}
	
	public NSString(String string){
		this.content = string;
	}
	
	public NSString(String string,Object ...args){
		this.content = String.format(string, args);
	}
	
	public static NSString string(){
		return new NSString();
	}
	
	//stringWithFormat
	public static NSString stringWithFormat(String string,Object ...args){
		return new NSString(string, args);
	}
	//stringByAppendingFormat
	public NSString stringByAppendingFormat(String string,Object ...args){
		return new NSString(string, args);
	}
	//stringByAppendingString
	public  NSString stringByAppendingString(String string){
		return  new NSString(this.content = string) ;
	}
	
	//ContentsOfFile
//	public static NSString stringWithContentsOfFile(String string) {
//		return new NSString(string);
//	}
	
	//initWithString
	public  NSString initWithString(String string) {
		this.content = string;
		return this;
	}

	//subStringFromIndex
	public NSString subStringFromIndex(int start){
		return new NSString(this.content.substring(start)) ;
	}
	//subStringFromIndexToIndex
	public NSString subStringToIndex(int end) {
		int start = 0;
		return new NSString(this.content.substring(start, end)) ;
	}
	//length
	public int length(){
		return  this.content.length();
	}
	
	//getString
	public String getString() {
		return this.content;
	}
	//setString
	public void setString (String string) {
		this.content = string;
	}
	//isEqualToString
	public boolean isEqualToString (NSString string){
		return this.content.equals(string.getString());
	}
	//characterAtIndex
	public char characterAtIndex(int index) {
		return this.content.charAt(index);
	}
	
	//substringWithRange
	public NSString substringWithRange(NSRange range) {
		 return new NSString(this.content.substring(range.start, range.end));
	}
	
	//×Ö·û½ØÈ¡×Ö·û´®
	public NSArray componentsSeparatedByString (String string) {
		String[] c = this.content.split(string);
		NSArray array = NSArray.arrayWithObjects((Object[])c);
		return array;
	}
	
	//stringByReplacingCharactersInRange
	public NSString stringByReplacingCharactersInRange (NSRange range,String string) {
		int length =this.length();
		String	stringBegin = this.content.substring(0,range.start);
		String	stringEnd = this.content.substring(range.end+1,length);
		return new NSString(stringBegin+string+stringEnd);
	}
	
	//pathExtension
	public NSString pathExtension () {
		NSArray array = this.componentsSeparatedByString(".");
		String string = "";
		for (int i =array.count()-1; i>0; i--) {
			if(!array.objectAtIndex(i).equals("")){
				if (i!=0) {
					string = (String)array.objectAtIndex(i);
					break;
				}
			}
		}
		return new NSString(string);
	}
	//lastPathComponent
	public NSString lastPathComponent () {
		NSArray array = this.componentsSeparatedByString("/");
		String string = "/";
	    for (int i =0; i<array.count(); i++) {
	        if (!array.objectAtIndex(i).equals("")) {
	            string =(String) array.objectAtIndex(i);
	        }
	    }
		return new NSString(string);
	}
	//pathComponents
	public NSArray pathComponents() {
		NSArray array = this.componentsSeparatedByString("/");
		NSMutableArray pathArray = NSMutableArray.array();
		String string = "/";
	    for (int i =0; i<array.count(); i++) {
	        if (!array.objectAtIndex(i).equals("")) {
	        	if (i!=0 && i!=array.count()-1) {
		            string =(String) array.objectAtIndex(i);
		            pathArray.add(string);
				}
	        }
	    }
		return NSArray.arrayWithArray(pathArray);
	}
	
//	//componentsSeparatedByString
//	public NSArray componentsSeparatedByString(String string) {
//
//	}
	
	//toCharArray
//	public char[] toCharArray() {
//		return this.content.toCharArray();	
//	}
	
//int,float,double,bool value
	public int intValue() {
		return Integer.parseInt(this.content);
	}
	public Float float1Value() {
		return Float.parseFloat(this.content);
	}
	public Double doubleValue() {
		return Double.parseDouble(this.content);
	}
	public Boolean boolValue() {
		return Boolean.parseBoolean(this.content);
	}
}
