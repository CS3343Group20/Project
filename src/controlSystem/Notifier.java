package controlSystem;

public interface Notifier {
	public void attach(Observer observer);
	public void detach(Observer observer);
	public void notifyObservers();
}
