package BookMyShow;

public interface Observable {
	void addObserver(Observer observer);
	void removeObserver(Observer observer);
	void notifyUser();
}
