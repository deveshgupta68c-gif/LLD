package DesignPatterns.ObserverPattern.Observable;

import DesignPatterns.ObserverPattern.Observer.Observer;

public interface Observable {
	void notifyUser();
	void addObserver(Observer observer);
	void removeObserver(Observer observer);
	void setData(int data);
}
