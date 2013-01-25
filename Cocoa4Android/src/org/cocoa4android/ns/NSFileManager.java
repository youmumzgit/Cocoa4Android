package org.cocoa4android.ns;


public class NSFileManager extends NSObject {
	private static NSFileManager defaultManager;
	public static NSFileManager defaultManager(){
		if (defaultManager==null) {
			defaultManager = new NSFileManager();
		}
		return defaultManager;
	}
	public boolean createDirectoryAtPath(String path,boolean createIntermediates,NSDictionary attributes,NSError error){
		
		return YES;
	}
	
	public static NSArray NSSearchPathForDirectoriesInDomains(){
		return null;
	}
}
