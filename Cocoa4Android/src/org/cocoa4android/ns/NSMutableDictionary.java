package org.cocoa4android.ns;

public class NSMutableDictionary extends NSDictionary {
	public static NSMutableDictionary dictionary(){
		return new NSMutableDictionary();
	}
	
	public void setObject(Object anObject,Object aKey){
		hashMap.put(aKey, anObject);
	}
	public void removeObject(Object aKey){
		hashMap.remove(aKey);
	}
}
