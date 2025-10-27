package DesignPatterns.ObserverPattern.Observer;

import java.util.ArrayList;
import java.util.List;

import DesignPatterns.ObserverPattern.Observable.Observable;

public class EmailObserver implements Observer{
	List<String> emails;
	public EmailObserver(Observable observable){
		observable.addObserver(this);
		emails = new ArrayList<>();
	}
	public void addObservable(Observable newObservable){
		newObservable.addObserver(this);
	}

	@Override
	public void addEmail(String email){
		if(!emails.contains(email)) {
			emails.add(email);
		}
	}
	public void removeEmail(String email){
		emails.remove(email);
	}
	@Override
	public void update() {
		emails.forEach(email ->{
			System.out.println("Email sent to : " + email);
		});
	}
}
