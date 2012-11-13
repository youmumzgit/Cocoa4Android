package org.cocoa4android.ns;

public class NSString extends NSObject {
	private String string;
	
	public static NSString stringWithFormat(String format,Object ...args){
		return new NSString(format, args);
	}
	public NSString(String format,Object...args){
		this.setString(String.format(format, args));
	}
	public String getString() {
		return string;
	}
	public void setString(String string) {
		this.string = string;
	}
	
}
