package org.cocoa4android.util.asihttp;

public interface ASICacheDelegate {
	public class ASICachePolicy{
		// The default cache policy. When you set a request to use this, it will use the cache's defaultCachePolicy
		// ASIDownloadCache's default cache policy is 'ASIAskServerIfModifiedWhenStaleCachePolicy'
		public static final int ASIUseDefaultCachePolicy = 0;

		// Tell the request not to read from the cache
		public static final int ASIDoNotReadFromCacheCachePolicy = 1;

		// The the request not to write to the cache
		public static final int ASIDoNotWriteToCacheCachePolicy = 2;

		// Ask the server if there is an updated version of this resource (using a conditional GET) ONLY when the cached data is stale
		public static final int ASIAskServerIfModifiedWhenStaleCachePolicy = 4;

		// Always ask the server if there is an updated version of this resource (using a conditional GET)
		public static final int ASIAskServerIfModifiedCachePolicy = 8;

		// If cached data exists, use it even if it is stale. This means requests will not talk to the server unless the resource they are requesting is not in the cache
		public static final int ASIOnlyLoadIfNotCachedCachePolicy = 16;

		// If cached data exists, use it even if it is stale. If cached data does not exist, stop (will not set an error on the request)
		public static final int ASIDontLoadCachePolicy = 32;

		// Specifies that cached data may be used if the request fails. If cached data is used, the request will succeed without error. Usually used in combination with other options above.
		public static final int ASIFallbackToCacheIfLoadFailsCachePolicy = 64;
	}
	
	// Cache storage policies control whether cached data persists between application launches (ASICachePermanentlyCacheStoragePolicy) or not (ASICachePermanentlyCacheStoragePolicy)
	// Calling [ASIHTTPRequest clearSession] will remove any data stored using ASICacheForSessionDurationCacheStoragePolicy
	public class ASICacheStoragePolicy{
		public static final int ASICacheForSessionDurationCacheStoragePolicy = 0;
		public static final int ASICachePermanentlyCacheStoragePolicy = 1;
	}
}
