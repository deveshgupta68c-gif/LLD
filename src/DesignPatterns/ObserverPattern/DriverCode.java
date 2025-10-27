package DesignPatterns.ObserverPattern;

import DesignPatterns.ObserverPattern.Observer.EmailObserver;
import DesignPatterns.ObserverPattern.Observer.Observer;
import DesignPatterns.ObserverPattern.Observable.IphoneObservable;
import DesignPatterns.ObserverPattern.Observable.Observable;

public class DriverCode {
	public static void main(String[] args) {
		Observable iphoneObservable = new IphoneObservable();
		Observer emailObserver = new EmailObserver(iphoneObservable);
		Observer emailObserver2 = new EmailObserver(iphoneObservable);
		emailObserver2.addEmail("abc@gmail.com");
		emailObserver2.addEmail("xyz@gmail.com");
		emailObserver.addEmail("yqsac@gmail.com");
		emailObserver.addEmail("asxc@gmail.com");
		iphoneObservable.setData(1);
	}
}