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
import java.util.Collection;
import java.util.Iterator;

public class NSMutableArray extends NSArray {
	ArrayList<Object> list = null;
	
	public static NSMutableArray array(){
		return new NSMutableArray();
	}
	public static NSMutableArray array(int capacity){
		return new NSMutableArray(capacity);
	}
	public NSMutableArray(int capacity){
		list = new ArrayList<Object>(capacity);
	}
	public NSMutableArray(){
		list = new ArrayList<Object>();
	}
	public void addObject(Object anObject){
		list.add(anObject);
	}
	public Object objectAtIndex(int index) {
		 return list.get(index);
	}
	public int count() {
		 return list.size();
	}
	
	@Override
	public boolean add(Object object) {
		// TODO Auto-generated method stub
		return list.add(object);
	}
	@Override
	public boolean addAll(Collection<? extends Object> arg0) {
		// TODO Auto-generated method stub
		return list.addAll(arg0);
	}
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		list.clear();
	}
	@Override
	public boolean contains(Object object) {
		// TODO Auto-generated method stub
		return list.contains(object);
	}
	@Override
	public boolean containsAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return list.containsAll(arg0);
	}
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return list.isEmpty();
	}
	@Override
	public Iterator<Object> iterator() {
		return list.iterator();
	}
	@Override
	public boolean remove(Object object) {
		// TODO Auto-generated method stub
		return list.remove(object);
	}
	@Override
	public boolean removeAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		
		return list.removeAll(arg0);
	}
	@Override
	public boolean retainAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return list.size();
	}
	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return list.toArray();
	}
	@Override
	public <T> T[] toArray(T[] array) {
		// TODO Auto-generated method stub
		return list.toArray(array);
	}
}
