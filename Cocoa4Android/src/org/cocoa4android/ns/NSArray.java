/*
 * Copyright (C) 2012 Wu Tong
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

import java.util.ArrayList;
import java.util.Collections;

public class NSArray extends NSObject{
	ArrayList<Object> list = null;
	
	/**
	 * Initialize an array using one object
	 * @param object
	 * @return
	 */
	public static NSArray arrayWithObject(Object object){
		return new NSArray(object);
	}
	/**
	 * Initialize an array using some objects
	 * @param objects
	 * @return
	 */
	public static NSArray arrayWithObjects(Object ...objects){
		return new NSArray(objects);
	}
	/**
	 * Initialize an array using another array
	 * @param array
	 * @return
	 */
	public static NSArray arrayWithArray(NSArray array){
		return new NSArray(array);
	}
	
	@SuppressWarnings("unchecked")
	public NSArray(NSArray array){
		this.list = (ArrayList<Object>) this.list.clone();
	}
	public NSArray(){
		list = new ArrayList<Object>();
	}
	public NSArray(Object ...objects){
		list = new ArrayList<Object>(objects.length);
		for (Object object : objects) {
			list.add(object);
		}
	}
	public NSArray(Object object){
		list = new ArrayList<Object>(1);
		list.add(object);
	}
	 
	public int count() {
		return list.size();
	}
	public Object objectAtIndex(int index) {
		return list.get(index);
	}
	
	public int indexOfObject(Object anObject){
		return list.indexOf(anObject);
	}
	
	public NSArray sortedArrayUsingComparator(NSComparator cmptr){
		NSArray array = NSArray.arrayWithArray(this);
		Collections.sort(array.list, cmptr);
		return array;
	}
	
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < this.count(); i++) {
			sb.append(this.objectAtIndex(i));
		}
		return sb.toString();
	}
}
