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

import android.R.integer;
import android.R.string;

public class NSString extends NSObject {
	String content = null;
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
	public static NSString stringWithContentsOfFile(String string) {
		return new NSString(string);
	}
	
	//initWithString
	public  NSString initWithString(String string) {
		this.content = string;
		return this;
	}

	//endsWith
	public boolean endsWith (NSString string){
		return this.endsWith(string);
	}
	//lastIndexOf
	public NSString lastIndexOf (NSString string){
		return this.lastIndexOf(string);
	}
	//×Ö´®³¤¶È
	public int length(){
		return  this.content.length();
	}
	
	//³éÈ¡×Ö´®
	public NSString subStringFromIndex(int start){
		return new NSString(this.content.substring(start)) ;
	}
	//subStringFromIndexToIndex
	public NSString subStringFromIndexToIndex(int start,int end) {
		return new NSString(this.content.substring(start, end)) ;
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
	
	//toCharArray
//	public char[] toCharArray() {
//		return this.content.toCharArray();	
//	}

}
