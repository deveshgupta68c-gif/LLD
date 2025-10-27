package DesignPatterns.ObserverPattern.Observer;

import DesignPatterns.ObserverPattern.Observable.Observable;

public interface Observer {
	void update();
	public void addObservable(Observable newObservable);
	void addEmail(String email);
}
