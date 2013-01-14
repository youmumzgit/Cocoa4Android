package org.cocoa4android.ns;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NSOperationQueue extends NSObject {
	private ExecutorService threadPool = Executors.newCachedThreadPool();
	private NSMutableArray operations;
	public NSArray operations() {
		return operations;
	}

	public int operationCount() {
		if (operations!=null) {
			return operations.count();
		}
		return 0;
	}
	private int maxConcurrentOperationCount;
	public int maxConcurrentOperationCount() {
		return maxConcurrentOperationCount;
	}

	public void setMaxConcurrentOperationCount(int cnt) {
		this.maxConcurrentOperationCount = cnt;
		threadPool = Executors.newFixedThreadPool(cnt);
	}
	
	public void addOperation(final NSOperation op){
		operations.addObject(op);
		threadPool.execute(new Runnable() {
			
			@Override
			public void run() {
				op.main();
			}
		});
	}
}
