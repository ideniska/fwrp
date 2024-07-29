package notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    private List<Observer> observers;

    public NotificationService() {
        this.observers = new ArrayList<>();
    }

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public void unregisterObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    public void clearObservers() {
        observers.clear();
    }
}
