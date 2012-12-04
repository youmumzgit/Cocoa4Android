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


import java.util.HashMap;

public class NSDictionary extends NSObject {
	protected HashMap<Object, Object> hashMap;
	
	public static NSDictionary dictionaryWithObjectsAndKeys(Object ...objects){
		return new NSDictionary(objects);
	}
	public NSDictionary(){
		hashMap = new HashMap<Object, Object>();
	}
	public NSDictionary(Object ...objects){
		hashMap  = new HashMap<Object, Object>(objects.length/2);
		int i =0;
		while(i<objects.length-2){
			Object value = objects[++i];
			Object key = objects[++i];
			hashMap.put(key, value);
		}
	}
	
	
	public Object objectForKey(Object akey){
		return hashMap.get(akey);
	}
	/*
	public Object[] allKeys(){
		return hashMap.keySet().toArray();
	}
	*/
	
	public NSArray allKeys(){
		Object[] objects = hashMap.keySet().toArray();
		return new NSArray(objects);
	}
	public int cout(){
		return hashMap.size();
	} 
}
