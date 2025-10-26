package ObserverPattern.Observable;

import java.util.ArrayList;
import java.util.List;

import ObserverPattern.Observer.Observer;

public class IphoneObservable implements Observable{
	List<Observer> fObserverList;
	int currentStock;

	public IphoneObservable(){
		currentStock = 0;
		fObserverList = new ArrayList<>();
	}

	@Override
	public void notifyUser() {
		fObserverList.forEach(Observer::update);
	}

	@Override
	public void addObserver(Observer observer) {
		fObserverList.add(observer);
	}

	@Override
	public void RemoveObserver(Observer observer) {
		fObserverList.remove(observer);
	}

	@Override
	public void setData(int data){
		if(currentStock != data){
			currentStock = data;
			if(currentStock >= 1) {
				notifyUser();
			}
		}
	}
}
