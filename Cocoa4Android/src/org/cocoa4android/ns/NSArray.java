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
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class NSArray extends NSObject implements Collection<Object>{
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
	@Override
	public boolean add(Object object) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean addAll(Collection<? extends Object> arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean contains(Object object) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean containsAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return objects.length == 0;
	}
	@Override
	public Iterator<Object> iterator() {
		return new NSArrayIterator(objects);
	}
	@Override
	public boolean remove(Object object) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean removeAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean retainAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return objects.length;
	}
	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return objects;
	}
	@Override
	public <T> T[] toArray(T[] array) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public class NSArrayIterator implements Iterator<Object>{
		List<Object> objectList;
		int index;
		public NSArrayIterator(Object[] objects){
			objectList = Arrays.asList(objects);
		}
		public NSArrayIterator(List<Object> list){
			this.objectList = list;
		}
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return index<objectList.size()-1;
		}

		@Override
		public Object next() {
			// TODO Auto-generated method stub
			return objectList.get(index++);
		}

		@Override
		public void remove() {
			objectList.remove(index);
		}
		
	}
}
