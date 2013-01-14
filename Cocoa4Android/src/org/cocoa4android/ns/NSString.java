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

import org.cocoa4android.util.sbjson.SBJsonParser;

public class NSString extends NSObject {
	protected String content = null;
	
	public static NSString stringWithJavaString(String string){
		return new NSString(string);
	}
	
	private NSString(){
		this.content = "";
	}
	
	private NSString(String string){
		this.content = string;
	}
	
	private NSString(String string,Object ...args){
		this.content = String.format(string, args);
	}
	
	public static NSString string(){
		return new NSString();
	}
	
	//stringWithFormat
	public static String stringWithFormat(String format,Object ...args){
		return String.format(format, args);
	}
	//stringByAppendingFormat
	public String stringByAppendingFormat(String string,String format,Object ...args){
		return string+String.format(format, args);
	}
	//stringByAppendingString
	public  String stringByAppendingString(String string){
		return this.content+string;
	}
	
	//ContentsOfFile
//	public static NSString stringWithContentsOfFile(String string) {
//		return new NSString(string);
//	}
	

	//subStringFromIndex
	public String subStringFromIndex(int start){
		return this.content.substring(start);
	}
	//subStringFromIndexToIndex
	public String subStringToIndex(int end) {
		int start = 0;
		return this.content.substring(start, end) ;
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
	public String substringWithRange(NSRange range) {
		 return this.content.substring(range.start, range.end);
	}
	
	//×Ö·û½ØÈ¡×Ö·û´®
	public NSArray componentsSeparatedByString (String string) {
		String  c[] = this.content.split(string);
		NSArray array = NSArray.arrayWithObjects((Object[])c);
		return array;
	}
	
	//stringByReplacingCharactersInRange
	public String stringByReplacingCharactersInRange (NSRange range,String string) {
		int length =this.length();
		String	stringBegin = this.content.substring(0,range.start);
		String	stringEnd = this.content.substring(range.end+1,length);
		return stringBegin+string+stringEnd;
	}
	
	//pathExtension (ok)
	public String pathExtension () {
		String string = "";
		//ÅÐ¶Ï×Ö·û´®ÖÐÊÇ·ñÓÐ ¡£ £¨£­1Ã»ÓÐ£©
		if (this.content.lastIndexOf(".")>0) {
			int length = this.content.length();
			int index = this.content.lastIndexOf(".");
			string = this.content.substring(index+1, length);
		} 
		/*
//		NSArray array = this.componentsSeparatedByString("\\.");
//		String string = "";
//		System.out.print(array.count());
//		int i = 0;
//		if (array.count()>1) {
//			i = array.count()-1;
//			if(!array.objectAtIndex(i).equals("") && !array.objectAtIndex(i).equals("/")){
//		           String sub="/";
//					if (array.objectAtIndex(i).toString().length()!=0 && i-1>0) {
//						sub = array.objectAtIndex(i).toString().substring(0, 1);
//					}
//		            if (!sub.equals("/")) {
//						string = (String)array.objectAtIndex(i);
//					}
//				}
//		}
 */
		return string;
	}
	//lastPathComponent (ok)
	public String lastPathComponent () {
//		NSArray array = this.componentsSeparatedByString("/");
//		String string = "/";
//	    for (int i =0; i<array.count(); i++) {
//	        if (!array.objectAtIndex(i).equals("")) {
//	            string =(String) array.objectAtIndex(i);
//	        }
//	    }
		int start = -1,end = -1;
		for (int i = content.length()-1; i >= 0; i--) {
			if (end == -1) {
				if (content.charAt(i) != '/') {
					end = i;
				}
			}
			else {
				if (content.charAt(i) == '/') {
					start = i;
					break;
				}
			}
		}
		if (end == -1) {
			return "/";
		}
		return content.substring(start + 1, end +1);
//		return new NSString(string);
	}
	//pathComponents (you hua)
	public NSArray pathComponents() {
//		NSArray array = this.componentsSeparatedByString("/");
		NSMutableArray pathArray = NSMutableArray.array();
		String string ;
//	    for (int i =0; i<array.count(); i++) {
//	        if (array.objectAtIndex(i).equals("")) {
//	        	if (i==0 || i==array.count()-1) {
//		            pathArray.add(string);
//				}
//	        }else {
//				string =(String) array.objectAtIndex(i);
//				pathArray.add(string);
//			}
//	    }
//		return pathArray;
		int start = 0,end = 0 ;
		for (int i = 0; i < content.length(); i++) {
			if (content.charAt(i)=='/' || i==length()-1) {
				end = i;
				string= content.substring(start,end+1);
				if (string.equals("/")) {
					if (start==0 || end==content.length()-1) {
						pathArray.addObject('/');
					}
				}else {
					pathArray.addObject(string);
				} 
				start = i+1;
			}
		}
		System.out.print(pathArray.count());
		return pathArray;
	}
	
	//stringByAppendingPathComponent (ok)
	public String stringByAppendingPathComponent(String string) {
		String receive=this.getString();
		if (receive.equals("")) {
			return string;
		}else {
			int index = this.content.length();
			int c = this.content.lastIndexOf("/");
			if (c==index-1) {
				receive = this.content+string;
			}else {
				receive = this.content+"/"+string;
			}
			return receive;
		}
	}
	
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
	public boolean boolValue() {
		return Integer.parseInt(this.content)!=0;
	}
	//toString
	public String toString() 	{
		return this.getString();
	}
	
	/**
	 * Returns the NSDictionary or NSArray represented by the receiver's JSON representation, or nil on error
	 * @return
	 */
	public Object JSONValue(){
		return SBJsonParser.objectWithString(this.getString());
	}
	
	public class NSStringEncoding{
		public static final int NSASCIIStringEncoding = 1;		/* 0..127 only */
		public static final int NSNEXTSTEPStringEncoding = 2;
		public static final int NSJapaneseEUCStringEncoding = 3;
		public static final int NSUTF8StringEncoding = 4;
		public static final int NSISOLatin1StringEncoding = 5;
		public static final int NSSymbolStringEncoding = 6;
		public static final int NSNonLossyASCIIStringEncoding = 7;
		public static final int NSShiftJISStringEncoding = 8;          /* kCFStringEncodingDOSJapanese */
		public static final int NSISOLatin2StringEncoding = 9;
		public static final int NSUnicodeStringEncoding = 10;
		public static final int NSWindowsCP1251StringEncoding = 11;    /* Cyrillic; same as AdobeStandardCyrillic */
		public static final int NSWindowsCP1252StringEncoding = 12;    /* WinLatin1 */
		public static final int NSWindowsCP1253StringEncoding = 13;    /* Greek */
		public static final int NSWindowsCP1254StringEncoding = 14;    /* Turkish */
		public static final int NSWindowsCP1250StringEncoding = 15;    /* WinLatin2 */
		public static final int NSISO2022JPStringEncoding = 21;        /* ISO 2022 Japanese encoding for e-mail */
		public static final int NSMacOSRomanStringEncoding = 30;

		public static final int NSUTF16StringEncoding = NSUnicodeStringEncoding;      /* An alias for NSUnicodeStringEncoding */

		public static final int NSUTF16BigEndianStringEncoding = 0x90000100;          /* NSUTF16StringEncoding encoding with explicit endianness specified */
		public static final int NSUTF16LittleEndianStringEncoding = 0x94000100;       /* NSUTF16StringEncoding encoding with explicit endianness specified */

		public static final int NSUTF32StringEncoding = 0x8c000100;                   
		public static final int NSUTF32BigEndianStringEncoding = 0x98000100;          /* NSUTF32StringEncoding encoding with explicit endianness specified */
		public static final int NSUTF32LittleEndianStringEncoding = 0x9c000100;       /* NSUTF32StringEncoding encoding with explicit endianness specified */
	}
}
