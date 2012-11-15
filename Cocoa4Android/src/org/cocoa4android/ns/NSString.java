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

import java.nio.charset.Charset;

import android.R.bool;
import android.R.integer;
import android.R.string;

public class NSString extends NSObject {
	protected String content = null;
	private  int length = 0;
	
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
	
	//字符截取字符串
	public NSArray componentsSeparatedByString (String string) {
		String[] c = this.content.split(string);
		NSArray array = NSArray.arrayWithObjects((Object[])c);
		return array;
	}
	
	stringByReplacingCharactersInRange:withString
	
//	//lastIndexOf
//	public NSString lastIndexOf (NSString string){
//		return this.lastIndexOf(string);
//	}
//
//	//copyValueOf
//	public String copyValueOf(char c[]) {
//		return this.copyValueOf(c);	
//	}
	
	//getCharactersOfrange ( .. ing)
//	public char getCharactersOfRange(NSRange()) {
//		return this.content.;
//	}

//	//找出子串的index，（start 开始位置）
//	public int IndexIsSearchSubstring(String string,int start) {
//		return this.content.lastIndexOf(string, start);
//	}
//
//	//containsOfSubstring
//	public boolean containsOfSubstring (String string){
//		return this.content.contains(string);
//	}
//	//字串是否正则
//	public boolean matches (String string){
//		return this.matches(string);
//	}
//	//endsWith
//	public boolean endsWith (NSString string){
//		return this.endsWith(string);
//	}
	
	//toCharArray
	public char[] toCharArray() {
		return this.content.toCharArray();	
	}

	public int intValue() {
		return Integer.parseInt(this.content);
	}
	public Double doubleValue() {
		return Double.parseDouble(this.content);
	}
	public Boolean boolValue() {
		return Boolean.parseBoolean(this.content);
	}
	
	public static void main(String c[]) {
		System.out.print("ssssss");
	}
}
