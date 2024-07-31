package notification;

/**
 * Subject interface for managing and notifying observers.
 * 
 * author Berkay
 */
public interface Subject {
    /**
     * Registers an observer to receive notifications.
     *
     * @param observer the observer to be registered
     */
    void registerObserver(Observer observer);

    /**
     * Removes an observer from receiving notifications.
     *
     * @param observer the observer to be removed
     */
    void removeObserver(Observer observer);

    /**
     * Notifies all registered observers with a message.
     *
     * @param message the message to be sent to the observers
     */
    void notifyObservers(String message);
}