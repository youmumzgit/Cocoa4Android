package org.cocoa4android.third.sbjson;

import org.cocoa4android.ns.NSArray;
import org.cocoa4android.ns.NSDictionary;
import org.cocoa4android.ns.NSMutableArray;
import org.cocoa4android.ns.NSMutableDictionary;
import org.cocoa4android.ns.NSObject;
import org.cocoa4android.ns.NSString;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

public class SBJsonParser extends NSObject {
	
	
	
	public NSObject objectWithString(NSString repr){
		String content = repr.getString();
		content = content.trim();
		if(content.startsWith("[")){
			//NSArray
			return this.serializeArray(repr);
		}else{
			return this.serializeDictionary(repr);
		}
	}
	private NSArray serializeArray(NSString repr){
		return null;
	}
	private NSDictionary serializeDictionary(NSString repr){
		return null;
	}
	/**
	 * 判断对象是否为空
	 * @param obj	实例
	 * @return
	 */
	private static boolean isNull(Object obj) {
		if (obj instanceof JSONObject) {
			return JSONObject.NULL.equals(obj);
		}
		return obj == null;
	}
}
