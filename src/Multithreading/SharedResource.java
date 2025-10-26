package Multithreading;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedResource {
	private final Lock fLock = new ReentrantLock();
	Integer value;
	public SharedResource(){
		value = 0;
	}
	public void readValue(String threadName){
		fLock.lock();
		try {
			System.out.println("LockAcquired for thread -> " + threadName);
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println("Exception thrown for thread: " + threadName);
		} finally {
			System.out.println("LockReleased for thread -> " + threadName);
			fLock.unlock();
		}
	}
}
