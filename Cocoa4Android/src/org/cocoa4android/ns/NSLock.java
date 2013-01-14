package org.cocoa4android.ns;

import java.util.concurrent.Semaphore;

public class NSLock extends NSObject {
	Mutex m = new Mutex();
	public NSLock(){
		
	}
	private boolean isLocked = NO;
	public void lock() {
		try {
			m.acquire();
			isLocked = YES;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	public boolean tryLock(){
		try {
			isLocked = m.attempt(1);
			return isLocked;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}
	public void unLock(){
		if (isLocked) {
			m.release();
			isLocked = NO;
		}
	}
	

	public class Mutex {
		private Semaphore s = new Semaphore(1);
		
		public void acquire() throws InterruptedException {
			s.acquire();
		}
		public void release(){
			s.release();
		}
		public boolean attempt(int ms) throws InterruptedException {
			return s.tryAcquire(ms);
		}
	}
}
