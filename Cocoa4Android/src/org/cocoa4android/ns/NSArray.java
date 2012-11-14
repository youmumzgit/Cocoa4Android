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

public class NSArray extends NSObject {
	 Object[] objects; 
	 public static NSArray arrayWithObject(Object object){
		 return new NSArray(object);
	 }
	 public static NSArray arrayWithObjects(Object ...objects){
		 return new NSArray(objects);
	 }
	 public NSArray(Object ...objects){
		 this.objects = objects;
	 }
	 public NSArray(Object object){
		 objects = new Object[1];
		 objects[0] = object;
	 }
	 
	 public int count() {
		 return objects.length;
	 }
	 public Object objectAtIndex(int index) {
		 return objects[index];
	 }
}
