package org.cocoa4android.util.asihttp;

public interface ASIProgressDelegate {
	// These methods are used to update UIProgressViews (iPhone OS) or NSProgressIndicators (Mac OS X)
	// If you are using a custom progress delegate, you may find it easier to implement didReceiveBytes / didSendBytes instead
	public void setProgress(float newProgress);
	public void setDoubleValue(double newProgress);
	public void setMaxValue(double newMax);
	
	// Called when the request receives some data - bytes is the length of that data
	public void didReceiveBytes(ASIHTTPRequest request,long bytes);
	
	// Called when the request sends some data
	// The first 32KB (128KB on older platforms) of data sent is not included in this amount because of limitations with the CFNetwork API
	// bytes may be less than zero if a request needs to remove upload progress (probably because the request needs to run again)
	public void didSendBytes(ASIHTTPRequest request,long bytes);
	
	// Called when a request needs to change the length of the content to download
	public void incrementDownloadSizeBy(ASIHTTPRequest request,long newLength);
	
	// Called when a request needs to change the length of the content to upload
	// newLength may be less than zero when a request needs to remove the size of the internal buffer from progress tracking
	public void incrementUploadSizeBy(ASIHTTPRequest request,long newLength);
}
