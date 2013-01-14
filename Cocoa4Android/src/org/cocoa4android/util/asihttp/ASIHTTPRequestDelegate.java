package org.cocoa4android.util.asihttp;

import org.cocoa4android.ns.NSData;
import org.cocoa4android.ns.NSDictionary;
import org.cocoa4android.ns.NSURL;

public interface ASIHTTPRequestDelegate {
	// These are the default delegate methods for request status
	// You can use different ones by setting didStartSelector / didFinishSelector / didFailSelector
	public void requestStarted(ASIHTTPRequest request);
	public void didReceiveResponseHeaders(ASIHTTPRequest request,NSDictionary responseHeaders);
	public void willRedirectToURL(ASIHTTPRequest request,NSURL newURL);
	public void requestFinished(ASIHTTPRequest request);
	public void requestFailed(ASIHTTPRequest request);
	public void requestRedirected(ASIHTTPRequest request);
	
	// When a delegate implements this method, it is expected to process all incoming data itself
	// This means that responseData / responseString / downloadDestinationPath etc are ignored
	// You can have the request call a different method by setting didReceiveDataSelector
	public void didReceiveData(ASIHTTPRequest request,NSData data);
	public void authenticationNeededForRequest(ASIHTTPRequest request);
	public void proxyAuthenticationNeededForRequest(ASIHTTPRequest request);
}
