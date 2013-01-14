package org.cocoa4android.ns;


public class NSError extends NSObject {
	private int code;
	private String domain;
	private NSDictionary _userInfo;
	public NSError(String domain,int code,NSDictionary userInfo){
		this.code = code;
		this.domain = domain;
		this._userInfo = userInfo;
	}
	public static NSError errorWithDomain(String domain,int code,NSDictionary userInfo){
		return new NSError(domain, code, userInfo);
	}
	public int code(){
		return this.code;
	}
	public String domain(){
		return domain;
	}
	public NSDictionary userInfo() {
		return this._userInfo;
	}
	//FIXME no description
	public String localizedDescription(){
		switch (code) {
		case 1:
			
			break;

		default:
			break;
		}
		return "";
	}
}
