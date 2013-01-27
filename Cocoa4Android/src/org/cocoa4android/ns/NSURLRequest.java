package org.cocoa4android.ns;

public class NSURLRequest extends NSObject {
	
	public static NSURLRequest requestWithURL(NSURL theURL){
		return new NSURLRequest(theURL);
	}
	NSURL URL;
	public NSURL URL() {
		return URL;
	}
	public void setURL(NSURL uRL) {
		URL = uRL;
	}
	public  NSURLRequest(NSURL theURL){
		this.URL = theURL;
	}
}
