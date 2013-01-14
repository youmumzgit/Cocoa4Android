package org.cocoa4android.util.asihttp;

import org.cocoa4android.cf.CFHTTPAuthenticationRef;
import org.cocoa4android.cf.CFHTTPMessageRef;
import org.cocoa4android.ns.NSArray;
import org.cocoa4android.ns.NSData;
import org.cocoa4android.ns.NSDate;
import org.cocoa4android.ns.NSDictionary;
import org.cocoa4android.ns.NSError;
import org.cocoa4android.ns.NSInputStream;
import org.cocoa4android.ns.NSLock;
import org.cocoa4android.ns.NSMutableArray;
import org.cocoa4android.ns.NSMutableData;
import org.cocoa4android.ns.NSMutableDictionary;
import org.cocoa4android.ns.NSNumber;
import org.cocoa4android.ns.NSOperation;
import org.cocoa4android.ns.NSOperationQueue;
import org.cocoa4android.ns.NSOutputStream;
import org.cocoa4android.ns.NSRecursiveLock;
import org.cocoa4android.ns.NSString.NSStringEncoding;
import org.cocoa4android.ns.NSThread;
import org.cocoa4android.ns.NSTimer;
import org.cocoa4android.ns.NSURL;
import org.cocoa4android.sec.SecIdentityRef;
import org.cocoa4android.ui.UIBackgroundTaskIdentifier;
import org.cocoa4android.ui.UIDevice;

public class ASIHTTPRequest extends NSOperation {
	
	// Automatically set on build
	String ASIHTTPRequestVersion = "v1.8-21 2010-12-04";

	final String NetworkRequestErrorDomain = "ASIHTTPRequestErrorDomain";

	static String ASIHTTPRequestRunLoopMode = "ASIHTTPRequestRunLoopMode";

	//static final CFOptionFlags kNetworkEvents =  kCFStreamEventHasBytesAvailable | kCFStreamEventEndEncountered | kCFStreamEventErrorOccurred;

	// In memory caches of credentials, used on when useSessionPersistence is YES
	static NSMutableArray sessionCredentialsStore = null;
	static NSMutableArray sessionProxyCredentialsStore = null;

	// This lock mediates access to session credentials
	static NSRecursiveLock sessionCredentialsLock = null;

	// We keep track of cookies we have received here so we can remove them from the sharedHTTPCookieStorage later
	static NSMutableArray sessionCookies = null;

	// The number of times we will allow requests to redirect before we fail with a redirection error
	final int RedirectionLimit = 5;

	// The default number of seconds to use for a timeout
	static double defaultTimeOutSeconds = 10;


	// This lock prevents the operation from being cancelled while it is trying to update the progress, and vice versa
	static NSRecursiveLock progressLock;

	static NSError ASIRequestCancelledError;
	static NSError ASIRequestTimedOutError;
	static NSError ASIAuthenticationError;
	static NSError ASIUnableToCreateRequestError;
	static NSError ASITooMuchRedirectionError;

	static NSMutableArray bandwidthUsageTracker = null;
	static long averageBandwidthUsedPerSecond = 0;


	// These are used for queuing persistent connections on the same connection

	// Incremented every time we specify we want a new connection
	static int nextConnectionNumberToCreate = 0;

	// An array of connectionInfo dictionaries.
	// When attempting a persistent connection, we look here to try to find an existing connection to the same server that is currently not in use
	static NSMutableArray persistentConnectionsPool = null;

	// Mediates access to the persistent connections pool
	static NSRecursiveLock connectionsLock = null;

	// Each request gets a new id, we store this rather than a ref to the request itself in the connectionInfo dictionary.
	// We do this so we don't have to keep the request around while we wait for the connection to expire
	static int nextRequestID = 0;

	// Records how much bandwidth all requests combined have used in the last second
	static long bandwidthUsedInLastSecond = 0; 

	// A date one second in the future from the time it was created
	static NSDate bandwidthMeasurementDate = null;

	// Since throttling variables are shared among all requests, we'll use a lock to mediate access
	static NSLock bandwidthThrottlingLock = null;

	// the maximum number of bytes that can be transmitted in one second
	static long maxBandwidthPerSecond = 0;

	// A default figure for throttling bandwidth on mobile devices
	final long ASIWWANBandwidthThrottleAmount = 14800;

	// YES when bandwidth throttling is active
	// This flag does not denote whether throttling is turned on - rather whether it is currently in use
	// It will be set to NO when throttling was turned on with setShouldThrottleBandwidthForWWAN, but a WI-FI connection is active
	static boolean isBandwidthThrottled = NO;

	// When YES, bandwidth will be automatically throttled when using WWAN (3G/Edge/GPRS)
	// Wifi will not be throttled
	static boolean shouldThrottleBandwithForWWANOnly = NO;

	// Mediates access to the session cookies so requests
	static NSRecursiveLock sessionCookiesLock = null;

	// This lock ensures delegates only receive one notification that authentication is required at once
	// When using ASIAuthenticationDialogs, it also ensures only one dialog is shown at once
	// If a request can't acquire the lock immediately, it means a dialog is being shown or a delegate is handling the authentication challenge
	// Once it gets the lock, it will try to look for existing credentials again rather than showing the dialog / notifying the delegate
	// This is so it can make use of any credentials supplied for the other request, if they are appropriate
	static NSRecursiveLock delegateAuthenticationLock = null;

	// When throttling bandwidth, Set to a date in future that we will allow all requests to wake up and reschedule their streams
	static NSDate throttleWakeUpTime = null;

	static ASICacheDelegate defaultCache = null;


	// Used for tracking when requests are using the network
	static  int runningRequestCount = 0;


	// You can use [ASIHTTPRequest setShouldUpdateNetworkActivityIndicator:NO] if you want to manage it yourself
	// Alternatively, override showNetworkActivityIndicator / hideNetworkActivityIndicator
	// By default this does nothing on Mac OS X, but again override the above methods for a different behaviour
	static boolean shouldUpdateNetworkActivityIndicator = YES;


	//**Queue stuff**/

	// The thread all requests will run on
	// Hangs around forever, but will be blocked unless there are requests underway
	static NSThread networkThread = null;

	static NSOperationQueue sharedQueue = null;
	
	static{
		persistentConnectionsPool = new NSMutableArray();
		connectionsLock = new NSRecursiveLock();
		progressLock = new NSRecursiveLock();
		bandwidthThrottlingLock = new NSLock();
		sessionCookiesLock = new NSRecursiveLock();
		sessionCredentialsLock = new NSRecursiveLock();
		delegateAuthenticationLock = new NSRecursiveLock();
		bandwidthUsageTracker = NSMutableArray.arrayWithCapacity(5);
		//FIXME no errors
		sharedQueue = new NSOperationQueue();
		sharedQueue.setMaxConcurrentOperationCount(4);
	}
	
	public static double defaultTimeOutSeconds(){
		return defaultTimeOutSeconds;
	}
	public static boolean isMultitaskingSupported()
	{
		boolean multiTaskingSupported = NO;
		if (UIDevice.currentDevice()!=null) {
			multiTaskingSupported =UIDevice.currentDevice().isMultitaskingSupported();
		}
		return multiTaskingSupported;
	}
	// The url for this operation, should include GET params in the query string where appropriate
	private NSURL url;
	public NSURL URL() {
		return url;
	}
	public void setURL(NSURL url) {
		this.url = url;
	}

	// Will always contain the original url used for making the request (the value of url can change when a request is redirected)
	private NSURL originalURL;
	public NSURL originalURL() {
		return originalURL;
	}
	public void setOriginalURL(NSURL originalURL) {
		this.originalURL = originalURL;
	}
	// Temporarily stores the url we are about to redirect to. Will be nil again when we do redirect
	private NSURL redirectURL;
	
	public NSURL redirectURL() {
		return redirectURL;
	}
	public void setRedirectURL(NSURL redirectURL) {
		this.redirectURL = redirectURL;
	}
	// The delegate, you need to manage setting and talking to your delegate in your subclasses
	private ASIHTTPRequestDelegate delegate;
	
	// HTTP method to use (GET / POST / PUT / DELETE / HEAD). Defaults to GET
	private String requestMethod;
	
	public String requestMethod() {
		return requestMethod;
	}
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	// Request body - only used when the whole body is stored in memory (shouldStreamPostDataFromDisk is false)
	private NSMutableData postBody;
	
	// gzipped request body used when shouldCompressRequestBody is YES
	private NSData compressedPostBody;
	
	// When true, post body will be streamed from a file on disk, rather than loaded into memory at once (useful for large uploads)
		// Automatically set to true in ASIFormDataRequests when using setFile:forKey:
	boolean shouldStreamPostDataFromDisk;
	
	// Path to file used to store post body (when shouldStreamPostDataFromDisk is true)
	// You can set this yourself - useful if you want to PUT a file from local disk 
	String postBodyFilePath;
	
	// Path to a temporary file used to store a deflated post body (when shouldCompressPostBody is YES)
	String compressedPostBodyFilePath;
	
	// Set to true when ASIHTTPRequest automatically created a temporary file containing the request body (when true, the file at postBodyFilePath will be deleted at the end of the request)
	boolean didCreateTemporaryPostDataFile;
	
	// Used when writing to the post body when shouldStreamPostDataFromDisk is true (via appendPostData: or appendPostDataFromFile:)
	NSOutputStream postBodyWriteStream;
	
	// Used for reading from the post body when sending the request
	NSInputStream postBodyReadStream;
	
	// Dictionary for custom HTTP request headers
	NSMutableDictionary requestHeaders;
	
	// Set to YES when the request header dictionary has been populated, used to prevent this happening more than once
	boolean haveBuiltRequestHeaders;
	
	// Will be populated with HTTP response headers from the server
	NSDictionary responseHeaders;
	
	public NSDictionary responseHeaders() {
		return responseHeaders;
	}
	public void setResponseHeaders(NSDictionary responseHeaders) {
		this.responseHeaders = responseHeaders;
	}
	// Can be used to manually insert cookie headers to a request, but it's more likely that sessionCookies will do this for you
	NSMutableArray requestCookies;
	
	public NSMutableArray requestCookies() {
		return requestCookies;
	}
	public void setRequestCookies(NSMutableArray requestCookies) {
		this.requestCookies = requestCookies;
	}

	// Will be populated with cookies
	NSArray responseCookies;
	
	// If use useCookiePersistence is true, network requests will present valid cookies from previous requests
	boolean useCookiePersistence;
	
	public boolean useCookiePersistence() {
		return useCookiePersistence;
	}
	public void setUseCookiePersistence(boolean useCookiePersistence) {
		this.useCookiePersistence = useCookiePersistence;
	}

	// If useKeychainPersistence is true, network requests will attempt to read credentials from the keychain, and will save them in the keychain when they are successfully presented
	boolean useKeychainPersistence;
	
	// If useSessionPersistence is true, network requests will save credentials and reuse for the duration of the session (until clearSession is called)
	boolean useSessionPersistence;
	
	public boolean useSessionPersistence() {
		return useSessionPersistence;
	}
	public void setUseSessionPersistence(boolean useSessionPersistence) {
		this.useSessionPersistence = useSessionPersistence;
	}

	// If allowCompressedResponse is true, requests will inform the server they can accept compressed data, and will automatically decompress gzipped responses. Default is true.
	boolean allowCompressedResponse;
	
	public boolean allowCompressedResponse() {
		return allowCompressedResponse;
	}
	public void setAllowCompressedResponse(boolean allowCompressedResponse) {
		this.allowCompressedResponse = allowCompressedResponse;
	}

	// If shouldCompressRequestBody is true, the request body will be gzipped. Default is false.
	// You will probably need to enable this feature on your webserver to make this work. Tested with apache only.
	boolean shouldCompressRequestBody;
	
	// When downloadDestinationPath is set, the result of this request will be downloaded to the file at this location
	// If downloadDestinationPath is not set, download data will be stored in memory
	String downloadDestinationPath;
	
	public String downloadDestinationPath() {
		return downloadDestinationPath;
	}
	public void setDownloadDestinationPath(String downloadDestinationPath) {
		this.downloadDestinationPath = downloadDestinationPath;
	}
	// The location that files will be downloaded to. Once a download is complete, files will be decompressed (if necessary) and moved to downloadDestinationPath
	String temporaryFileDownloadPath;
	
	// If the response is gzipped and shouldWaitToInflateCompressedResponses is NO, a file will be created at this path containing the inflated response as it comes in
	String temporaryUncompressedDataDownloadPath;
	
	// Used for writing data to a file when downloadDestinationPath is set
	NSOutputStream fileDownloadOutputStream;
	
	NSOutputStream inflatedFileDownloadOutputStream;
	
	// When the request fails or completes successfully, complete will be true
	boolean complete;
	
    public boolean complete() {
		return complete;
	}
	public void setComplete(boolean complete) {
		this.complete = complete;
	}
	// external "finished" indicator, subject of KVO notifications; updates after 'complete'
	boolean finished;
    
    // True if our 'cancel' selector has been called
	boolean cancelled;
	
	// If an error occurs, error will contain an NSError
	// If error code is = ASIConnectionFailureErrorType (1, Connection failure occurred) - inspect [[error userInfo] objectForKey:NSUnderlyingErrorKey] for more information
	NSError error;
	
	public NSError error() {
		return error;
	}
	public void setError(NSError error) {
		this.error = error;
	}

	// Username and password used for authentication
	String username;
	String password;
	
	// Domain used for NTLM authentication
	String domain;
	
	// Username and password used for proxy authentication
	String proxyUsername;
	String proxyPassword;
	
	// Domain used for NTLM proxy authentication
	String proxyDomain;
	
	// Delegate for displaying upload progress (usually an NSProgressIndicator, but you can supply a different object and handle this yourself)
	ASIProgressDelegate uploadProgressDelegate;
	
	// Delegate for displaying download progress (usually an NSProgressIndicator, but you can supply a different object and handle this yourself)
	ASIProgressDelegate downloadProgressDelegate;
	
	// Whether we've seen the headers of the response yet
    boolean haveExaminedHeaders;
    
    // Data we receive will be stored here. Data may be compressed unless allowCompressedResponse is false - you should use [request responseData] instead in most cases
 	NSMutableData rawResponseData;
 	
 	public NSMutableData rawResponseData() {
		return rawResponseData;
	}
	public void setRawResponseData(NSMutableData rawResponseData) {
		this.rawResponseData = rawResponseData;
	}
	CFHTTPMessageRef request;
 	
 	NSInputStream readStream;
 	
 	// Used for authentication
    CFHTTPAuthenticationRef requestAuthentication; 
	NSDictionary requestCredentials;
	
	// Used during NTLM authentication
	int authenticationRetryCount;
	
	// Authentication scheme (Basic, Digest, NTLM)
	String authenticationScheme;
	
	// Realm for authentication when credentials are required
	String authenticationRealm;
	
	// When YES, ASIHTTPRequest will present a dialog allowing users to enter credentials when no-matching credentials were found for a server that requires authentication
	// The dialog will not be shown if your delegate responds to authenticationNeededForRequest:
	// Default is NO.
	boolean shouldPresentAuthenticationDialog;
	
	// When YES, ASIHTTPRequest will present a dialog allowing users to enter credentials when no-matching credentials were found for a proxy server that requires authentication
	// The dialog will not be shown if your delegate responds to proxyAuthenticationNeededForRequest:
	// Default is YES (basically, because most people won't want the hassle of adding support for authenticating proxies to their apps)
	boolean shouldPresentProxyAuthenticationDialog;	
	
	public boolean shouldPresentProxyAuthenticationDialog() {
		return shouldPresentProxyAuthenticationDialog;
	}
	public void setShouldPresentProxyAuthenticationDialog(
			boolean shouldPresentProxyAuthenticationDialog) {
		this.shouldPresentProxyAuthenticationDialog = shouldPresentProxyAuthenticationDialog;
	}

	// Used for proxy authentication
    CFHTTPAuthenticationRef proxyAuthentication; 
	NSDictionary proxyCredentials;
	
	// Used during authentication with an NTLM proxy
	int proxyAuthenticationRetryCount;
	
	// Authentication scheme for the proxy (Basic, Digest, NTLM)
	String proxyAuthenticationScheme;	
	
	// Realm for proxy authentication when credentials are required
	String proxyAuthenticationRealm;
	
	
	// HTTP status code, eg: 200 = OK, 404 = Not found etc
	int responseStatusCode;
	
	// Description of the HTTP status code
	String responseStatusMessage;
	
	// Size of the response
	long contentLength;
	
	public long contentLength() {
		return contentLength;
	}
	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}
	// Size of the partially downloaded content
	long partialDownloadSize;
	
	// Size of the POST payload
	long postLength;	
	
	// The total amount of downloaded data
	long totalBytesRead;
	
	public long totalBytesRead() {
		return totalBytesRead;
	}
	public void setTotalBytesRead(long totalBytesRead) {
		this.totalBytesRead = totalBytesRead;
	}
	// The total amount of uploaded data
	long totalBytesSent;
	
	// Last amount of data read (used for incrementing progress)
	long lastBytesRead;
	
	public long lastBytesRead() {
		return lastBytesRead;
	}
	public void setLastBytesRead(long lastBytesRead) {
		this.lastBytesRead = lastBytesRead;
	}
	// Last amount of data sent (used for incrementing progress)
	long lastBytesSent;
	
	public long lastBytesSent() {
		return lastBytesSent;
	}
	public void setLastBytesSent(long lastBytesSent) {
		this.lastBytesSent = lastBytesSent;
	}
	// This lock prevents the operation from being cancelled at an inopportune moment
	NSRecursiveLock cancelledLock;
	
	public NSRecursiveLock cancelledLock() {
		return cancelledLock;
	}
	public void setCancelledLock(NSRecursiveLock cancelledLock) {
		this.cancelledLock = cancelledLock;
	}

	// Called on the delegate (if implemented) when the request starts. Default is requestStarted:
	String didStartSelector;
	
	// Called on the delegate (if implemented) when the request receives response headers. Default is requestDidReceiveResponseHeaders:
	String didReceiveResponseHeadersSelector;

	// Called on the delegate (if implemented) when the request receives a Location header and shouldRedirect is YES
	// The delegate can then change the url if needed, and can restart the request by calling [request resume], or simply cancel it
	String willRedirectSelector;

	// Called on the delegate (if implemented) when the request completes successfully. Default is requestFinished:
	String didFinishSelector;
	
	// Called on the delegate (if implemented) when the request fails. Default is requestFailed:
	String didFailSelector;
	
	// Called on the delegate (if implemented) when the request receives data. Default is request:didReceiveData:
	// If you set this and implement the method in your delegate, you must handle the data yourself - ASIHTTPRequest will not populate responseData or write the data to downloadDestinationPath
	String didReceiveDataSelector;
	
	// Used for recording when something last happened during the request, we will compare this value with the current date to time out requests when appropriate
	NSDate lastActivityTime;
	
	// Number of seconds to wait before timing out - default is 10
	double timeOutSeconds;
	
	public double timeOutSeconds() {
		return timeOutSeconds;
	}
	public void setTimeOutSeconds(double timeOutSeconds) {
		this.timeOutSeconds = timeOutSeconds;
	}
	
	//block to execute when request starts
	ASIBasicBlock startedBlock;

	public ASIBasicBlock startedBlock() {
		return startedBlock;
	}
	public void setStartedBlock(ASIBasicBlock startedBlock) {
		this.startedBlock = startedBlock;
	}

	//block to execute when headers are received
	ASIHeadersBlock headersReceivedBlock;

	//block to execute when request completes successfully
	ASIBasicBlock completionBlock;

	public ASIBasicBlock completionBlock() {
		return completionBlock;
	}
	public void setCompletionBlock(ASIBasicBlock completionBlock) {
		this.completionBlock = completionBlock;
	}

	//block to execute when request fails
	ASIBasicBlock failureBlock;

	public ASIBasicBlock failureBlock() {
		return failureBlock;
	}
	public void setFailureBlock(ASIBasicBlock failureBlock) {
		this.failureBlock = failureBlock;
	}

	//block for when bytes are received
	ASIProgressBlock bytesReceivedBlock;

	//block for when bytes are sent
	ASIProgressBlock bytesSentBlock;

	//block for when download size is incremented
	ASISizeBlock downloadSizeIncrementedBlock;

	//block for when upload size is incremented
	ASISizeBlock uploadSizeIncrementedBlock;

	//block for handling raw bytes received
	ASIDataBlock dataReceivedBlock;

	//block for handling authentication
	ASIBasicBlock authenticationNeededBlock;

	//block for handling proxy authentication
	ASIBasicBlock proxyAuthenticationNeededBlock;
	
    //block for handling redirections, if you want to
    ASIBasicBlock requestRedirectedBlock;
    
    public interface ASIBasicBlock{
		public abstract void function();
	}
    public interface ASIHeadersBlock{
		public abstract void function(NSDictionary responseHeaders);
	}
    public interface ASISizeBlock{
		public abstract void function(long size);
	}
    public interface ASIProgressBlock{
		public abstract void function(long size,long total);
	}
    public interface ASIDataBlock{
		public abstract void function(NSData data);
	}
	// Will be YES when a HEAD request will handle the content-length before this request starts
	boolean shouldResetUploadProgress;
	public boolean shouldResetUploadProgress() {
		return shouldResetUploadProgress;
	}
	public void setShouldResetUploadProgress(boolean shouldResetUploadProgress) {
		this.shouldResetUploadProgress = shouldResetUploadProgress;
	}

	boolean shouldResetDownloadProgress;
	
	public boolean shouldResetDownloadProgress() {
		return shouldResetDownloadProgress;
	}
	public void setShouldResetDownloadProgress(boolean shouldResetDownloadProgress) {
		this.shouldResetDownloadProgress = shouldResetDownloadProgress;
	}

	// Used by HEAD requests when showAccurateProgress is YES to preset the content-length for this request
	ASIHTTPRequest mainRequest;
	
	public ASIHTTPRequest mainRequest() {
		return mainRequest;
	}
	public void setMainRequest(ASIHTTPRequest mainRequest) {
		this.mainRequest = mainRequest;
	}
	// When NO, this request will only update the progress indicator when it completes
	// When YES, this request will update the progress indicator according to how much data it has received so far
	// The default for requests is YES
	// Also see the comments in ASINetworkQueue.h
	boolean showAccurateProgress;
	
	public boolean showAccurateProgress() {
		return showAccurateProgress;
	}
	public void setShowAccurateProgress(boolean showAccurateProgress) {
		this.showAccurateProgress = showAccurateProgress;
	}

	// Used to ensure the progress indicator is only incremented once when showAccurateProgress = NO
	boolean updatedProgress;
	
	// Prevents the body of the post being built more than once (largely for subclasses)
	boolean haveBuiltPostBody;
	
	// Used internally, may reflect the size of the internal buffer used by CFNetwork
	// POST / PUT operations with body sizes greater than uploadBufferSize will not timeout unless more than uploadBufferSize bytes have been sent
	// Likely to be 32KB on iPhone 3.0, 128KB on Mac OS X Leopard and iPhone 2.2.x
	long uploadBufferSize;
	
	// Text encoding for responses that do not send a Content-Type with a charset value. Defaults to NSISOLatin1StringEncoding
	int defaultResponseEncoding;
		
	public int defaultResponseEncoding() {
		return defaultResponseEncoding;
	}
	public void setDefaultResponseEncoding(int defaultResponseEncoding) {
		this.defaultResponseEncoding = defaultResponseEncoding;
	}

	// The text encoding of the response, will be defaultResponseEncoding if the server didn't specify. Can't be set.
	int responseEncoding;
	
	// Tells ASIHTTPRequest not to delete partial downloads, and allows it to use an existing file to resume a download. Defaults to NO.
	boolean allowResumeForFileDownloads;
	
	// Custom user information associated with the request
	NSDictionary userInfo;
	
	// Use HTTP 1.0 rather than 1.1 (defaults to false)
	boolean useHTTPVersionOne;
	
	// When YES, requests will automatically redirect when they get a HTTP 30x header (defaults to YES)
	boolean shouldRedirect;
	
	public boolean shouldRedirect() {
		return shouldRedirect;
	}
	public void setShouldRedirect(boolean shouldRedirect) {
		this.shouldRedirect = shouldRedirect;
	}

	// Used internally to tell the main loop we need to stop and retry with a new url
	boolean needsRedirect;
	
	// Incremented every time this request redirects. When it reaches 5, we give up
	int redirectCount;
	
	public int redirectCount() {
		return redirectCount;
	}
	public void setRedirectCount(int redirectCount) {
		this.redirectCount = redirectCount;
	}
	// When NO, requests will not check the secure certificate is valid (use for self-signed certificates during development, DO NOT USE IN PRODUCTION) Default is YES
	boolean validatesSecureCertificate;
	
	public boolean validatesSecureCertificate() {
		return validatesSecureCertificate;
	}
	public void setValidatesSecureCertificate(boolean validatesSecureCertificate) {
		this.validatesSecureCertificate = validatesSecureCertificate;
	}

	// If not nil and the URL scheme is https, CFNetwork configured to supply a client certificate
    SecIdentityRef clientCertificateIdentity;
	NSArray clientCertificates;
	
	// Details on the proxy to use - you could set these yourself, but it's probably best to let ASIHTTPRequest detect the system proxy settings
	String proxyHost;
	int proxyPort;
	
	// ASIHTTPRequest will assume kCFProxyTypeHTTP if the proxy type could not be automatically determined
	// Set to kCFProxyTypeSOCKS if you are manually configuring a SOCKS proxy
	String proxyType;

	// URL for a PAC (Proxy Auto Configuration) file. If you want to set this yourself, it's probably best if you use a local file
	NSURL PACurl;
	
	// See ASIAuthenticationState values above. 0 == default == No authentication needed yet
	ASIAuthenticationState authenticationNeeded;
	
	// When YES, ASIHTTPRequests will present credentials from the session store for requests to the same server before being asked for them
	// This avoids an extra round trip for requests after authentication has succeeded, which is much for efficient for authenticated requests with large bodies, or on slower connections
	// Set to NO to only present credentials when explicitly asked for them
	// This only affects credentials stored in the session cache when useSessionPersistence is YES. Credentials from the keychain are never presented unless the server asks for them
	// Default is YES
	boolean shouldPresentCredentialsBeforeChallenge;
	
	public boolean shouldPresentCredentialsBeforeChallenge() {
		return shouldPresentCredentialsBeforeChallenge;
	}
	public void setShouldPresentCredentialsBeforeChallenge(
			boolean shouldPresentCredentialsBeforeChallenge) {
		this.shouldPresentCredentialsBeforeChallenge = shouldPresentCredentialsBeforeChallenge;
	}

	// YES when the request hasn't finished yet. Will still be YES even if the request isn't doing anything (eg it's waiting for delegate authentication). READ-ONLY
	boolean inProgress;
	
	// Used internally to track whether the stream is scheduled on the run loop or not
	// Bandwidth throttling can unschedule the stream to slow things down while a request is in progress
	boolean readStreamIsScheduled;
	
	// Set to allow a request to automatically retry itself on timeout
	// Default is zero - timeout will stop the request
	int numberOfTimesToRetryOnTimeout;

	// The number of times this request has retried (when numberOfTimesToRetryOnTimeout > 0)
	int retryCount;
	
	// When YES, requests will keep the connection to the server alive for a while to allow subsequent requests to re-use it for a substantial speed-boost
	// Persistent connections will not be used if the server explicitly closes the connection
	// Default is YES
	boolean shouldAttemptPersistentConnection;

	public boolean shouldAttemptPersistentConnection() {
		return shouldAttemptPersistentConnection;
	}
	public void setShouldAttemptPersistentConnection(
			boolean shouldAttemptPersistentConnection) {
		this.shouldAttemptPersistentConnection = shouldAttemptPersistentConnection;
	}

	// Number of seconds to keep an inactive persistent connection open on the client side
	// Default is 60
	// If we get a keep-alive header, this is this value is replaced with how long the server told us to keep the connection around
	// A future date is created from this and used for expiring the connection, this is stored in connectionInfo's expires value
	double persistentConnectionTimeoutSeconds;
	
	public double persistentConnectionTimeoutSeconds() {
		return persistentConnectionTimeoutSeconds;
	}
	public void setPersistentConnectionTimeoutSeconds(
			double persistentConnectionTimeoutSeconds) {
		this.persistentConnectionTimeoutSeconds = persistentConnectionTimeoutSeconds;
	}

	// Set to yes when an appropriate keep-alive header is found
	boolean connectionCanBeReused;
	
	// Stores information about the persistent connection that is currently in use.
	// It may contain:
	// * The id we set for a particular connection, incremented every time we want to specify that we need a new connection
	// * The date that connection should expire
	// * A host, port and scheme for the connection. These are used to determine whether that connection can be reused by a subsequent request (all must match the new request)
	// * An id for the request that is currently using the connection. This is used for determining if a connection is available or not (we store a number rather than a reference to the request so we don't need to hang onto a request until the connection expires)
	// * A reference to the stream that is currently using the connection. This is necessary because we need to keep the old stream open until we've opened a new one.
	//   The stream will be closed + released either when another request comes to use the connection, or when the timer fires to tell the connection to expire
	NSMutableDictionary connectionInfo;
	
	// When set to YES, 301 and 302 automatic redirects will use the original method and and body, according to the HTTP 1.1 standard
	// Default is NO (to follow the behaviour of most browsers)
	boolean shouldUseRFC2616RedirectBehaviour;
	
	// Used internally to record when a request has finished downloading data
	boolean downloadComplete;
	
	public boolean downloadComplete() {
		return downloadComplete;
	}
	public void setDownloadComplete(boolean downloadComplete) {
		this.downloadComplete = downloadComplete;
	}
	// An ID that uniquely identifies this request - primarily used for debugging persistent connections
	NSNumber requestID;
	
	// Will be ASIHTTPRequestRunLoopMode for synchronous requests, NSDefaultRunLoopMode for all other requests
	String runLoopMode;
	
	public String runLoopMode() {
		return runLoopMode;
	}
	public void setRunLoopMode(String runLoopMode) {
		this.runLoopMode = runLoopMode;
	}

	// This timer checks up on the request every 0.25 seconds, and updates progress
	NSTimer statusTimer;
	
	// The download cache that will be used for this request (use [ASIHTTPRequest setDefaultCache:cache] to configure a default cache
	ASICacheDelegate downloadCache;
	
	public ASICacheDelegate downloadCache() {
		return downloadCache;
	}
	public void setDownloadCache(ASICacheDelegate downloadCache) {
		this.downloadCache = downloadCache;
	}

	// The cache policy that will be used for this request - See ASICacheDelegate.h for possible values
	int cachePolicy;
		
	// The cache storage policy that will be used for this request - See ASICacheDelegate.h for possible values
	int cacheStoragePolicy;
	
	// Will be true when the response was pulled from the cache rather than downloaded
	boolean didUseCachedResponse;

	public boolean didUseCachedResponse() {
		return didUseCachedResponse;
	}
	public void setDidUseCachedResponse(boolean didUseCachedResponse) {
		this.didUseCachedResponse = didUseCachedResponse;
	}
	// Set secondsToCache to use a custom time interval for expiring the response when it is stored in a cache
	int secondsToCache;

	boolean shouldContinueWhenAppEntersBackground;
	UIBackgroundTaskIdentifier backgroundTask;
	
	// When downloading a gzipped response, the request will use this helper object to inflate the response
	ASIDataDecompressor dataDecompressor;
	
	// Controls how responses with a gzipped encoding are inflated (decompressed)
	// When set to YES (This is the default):
	// * gzipped responses for requests without a downloadDestinationPath will be inflated only when [request responseData] / [request responseString] is called
	// * gzipped responses for requests with a downloadDestinationPath set will be inflated only when the request completes
	//
	// When set to NO
	// All requests will inflate the response as it comes in
	// * If the request has no downloadDestinationPath set, the raw (compressed) response is discarded and rawResponseData will contain the decompressed response
	// * If the request has a downloadDestinationPath, the raw response will be stored in temporaryFileDownloadPath as normal, the inflated response will be stored in temporaryUncompressedDataDownloadPath
	//   Once the request completes successfully, the contents of temporaryUncompressedDataDownloadPath are moved into downloadDestinationPath
	//
	// Setting this to NO may be especially useful for users using ASIHTTPRequest in conjunction with a streaming parser, as it will allow partial gzipped responses to be inflated and passed on to the parser while the request is still running
	boolean shouldWaitToInflateCompressedResponses;
	
	public boolean shouldWaitToInflateCompressedResponses() {
		return shouldWaitToInflateCompressedResponses;
	}
	public void setShouldWaitToInflateCompressedResponses(
			boolean shouldWaitToInflateCompressedResponses) {
		this.shouldWaitToInflateCompressedResponses = shouldWaitToInflateCompressedResponses;
	}
	public ASIHTTPRequest(NSURL newURL){
		this.setRequestMethod("GET");
		this.setRunLoopMode(NSDefaultRunLoopMode);
		this.setShouldAttemptPersistentConnection(YES);
		this.setPersistentConnectionTimeoutSeconds(60.0);
		this.setShouldPresentCredentialsBeforeChallenge(YES);
		this.setShouldRedirect(YES);
		this.setShowAccurateProgress(YES);
		this.setShouldResetDownloadProgress(YES);
		this.setShouldResetUploadProgress(YES);
		this.setAllowCompressedResponse(YES);
		this.setShouldWaitToInflateCompressedResponses(YES);
		this.setDefaultResponseEncoding(NSStringEncoding.NSISOLatin1StringEncoding);
		this.setShouldPresentProxyAuthenticationDialog(YES);
		
		this.setTimeOutSeconds(ASIHTTPRequest.defaultTimeOutSeconds());
		this.setUseSessionPersistence(YES);
		this.setUseSessionPersistence(YES);
		this.setValidatesSecureCertificate(YES);
		
		this.setRequestCookies(new NSMutableArray());
		this.setURL(newURL);
		this.setCancelledLock(new NSRecursiveLock());
		
		this.setDownloadCache(defaultCache);
	}
	public static ASIHTTPRequest requestWithURL(NSURL newURL){
		return new ASIHTTPRequest(newURL);
	}
	
	
	public void startAsynchronous(){
		sharedQueue.addOperation(this);
	}
	
	public class ASIAuthenticationState{
		public static final int ASINoAuthenticationNeededYet = 0;
		public static final int ASIHTTPAuthenticationNeeded = 1;
		public static final int ASIProxyAuthenticationNeeded = 2;
	}
	
	public class ASINetworkErrorType{
		public static final int ASIConnectionFailureErrorType = 1;
		public static final int ASIRequestTimedOutErrorType = 2;
		public static final int ASIAuthenticationErrorType = 3;
		public static final int ASIRequestCancelledErrorType = 4;
		public static final int ASIUnableToCreateRequestErrorType = 5;
		public static final int ASIInternalErrorWhileBuildingRequestType  = 6;
		public static final int ASIInternalErrorWhileApplyingCredentialsType  = 7;
		public static final int ASIFileManagementError = 8;
		public static final int ASITooMuchRedirectionErrorType = 9;
		public static final int ASIUnhandledExceptionError = 10;
		public static final int ASICompressionError = 11;
	}

	@Override
	public void main() {
		this.setComplete(NO);
		this.setDidUseCachedResponse(NO);
		if (this.url==null) {
			//TODO error
			return;
		}
		// Must call before we create the request so that the request method can be set if needs be
		if (this.mainRequest!=null) {
			this.buildPostBody();
		}
		if (this.requestMethod().equals("GET")) {
			this.setDownloadCache(null);
		}
		// If we're redirecting, we'll already have a CFHTTPMessageRef
		if (request!=null) {
			
		}
		
		//If this is a HEAD request generated by an ASINetworkQueue, we need to let the main request generate its headers first so we can use them
		if (this.mainRequest()!=null) {
			this.mainRequest().buildRequestHeaders();
		}
		
		// Even if this is a HEAD request with a mainRequest, we still need to call to give subclasses a chance to add their own to HEAD requests (ASIS3Request does this)
		this.buildRequestHeaders();
		if (this.downloadCache()!=null) {
			
		}
		this.applyAuthorizationHeader();
		String header;
		for (int i = 0; i < requestHeaders.cout(); i++) {
			
		}
		this.startRequest();
	}
	public void requestStarted(){
		if (startedBlock!=null) {
			startedBlock.function();
		}
	}
	public void startRequest(){
		this.performSelectorOnMainThread(selector("requestStarted"), null, NSThread.isMainThread());
		this.setDownloadComplete(NO);
		this.setComplete(NO);
		this.setTotalBytesRead(0);
		this.setLastBytesRead(0);
		if (this.redirectCount()==0) {
			this.setOriginalURL(this.url);
		}
		if (this.lastBytesSent()>0) {
			this.removeUploadProgressSoFar();
		}
		this.setLastBytesSent(0);
		this.setContentLength(0);
		this.setResponseHeaders(null);
		if (this.downloadDestinationPath()!=null) {
			this.setRawResponseData(new NSMutableData());
		}
	}
	public void removeUploadProgressSoFar(){}
	public void buildPostBody(){
		
	}
	public void buildRequestHeaders(){
		
	}
	public void applyAuthorizationHeader(){}
}
