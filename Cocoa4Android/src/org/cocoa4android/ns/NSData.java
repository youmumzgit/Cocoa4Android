package org.cocoa4android.ns;

public class NSData extends NSObject {
	private byte[] bytes;
	public NSData(){
		
	}
	public NSData(byte[] bytes,int length){
		this.bytes = bytes;
	}
	
	public byte[] bytes() {
		return bytes;
	}
	
}
