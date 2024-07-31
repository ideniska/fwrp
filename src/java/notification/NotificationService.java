package notification;

import java.util.ArrayList;
import java.util.List;

/**
 * NotificationService manages a list of observers and notifies them with messages.
 * 
 * author Berkay
 */
public class NotificationService {
    private List<Observer> observers;

    /**
     * Constructs a NotificationService with an empty list of observers.
     */
    public NotificationService() {
        this.observers = new ArrayList<>();
    }

    /**
     * Registers an observer to receive notifications.
     *
     * @param observer the observer to be registered
     */
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Unregisters an observer from receiving notifications.
     *
     * @param observer the observer to be unregistered
     */
    public void unregisterObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all registered observers with a message.
     *
     * @param message the message to be sent to the observers
     */
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    /**
     * Clears all registered observers.
     */
    public void clearObservers() {
        observers.clear();
    }
}