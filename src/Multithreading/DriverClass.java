package Multithreading;

import java.util.ArrayList;
import java.util.List;

public class DriverClass {
	public static void main(String[] args) {
		SharedResource sharedResource = new SharedResource();
		Thread1 thread1 = new Thread1(sharedResource, "thread1");
		Thread1 thread2 = new Thread1(sharedResource, "thread2");
		new Thread(thread1).start();
		new Thread(thread2).start();
		System.out.println("Exit");
	}
}
