package org.cocoa4android.ns;

public class NSRange extends NSObject{
	public int start = 0;
	public int end = 0;
	public NSRange (int start,int end) {
		if (start<end) {
			this.start=start;
			this.end = end;
		}
	}
}
