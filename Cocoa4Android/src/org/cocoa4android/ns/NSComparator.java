package org.cocoa4android.ns;

import java.util.Comparator;

public abstract class NSComparator extends NSObject implements Comparator<Object> {
	public abstract int NSComparisonResult(Object obj1,Object obj2);
	public int compare(java.lang.Object obj1, java.lang.Object obj2){
		return this.NSComparisonResult(obj1, obj2);
	}
}
