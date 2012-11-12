package org.cocoa4android.ns;

import java.util.HashMap;

import android.R.integer;

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
	
	public Object[] allKeys(){
		return hashMap.keySet().toArray();
	}
	public int cout(){
		return hashMap.size();
	} 
}
