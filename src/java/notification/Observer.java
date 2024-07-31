package notification;

/**
 * Observer interface for receiving notifications.
 * 
 * author Berkay
 */
public interface Observer {
    /**
     * Updates the observer with a message.
     *
     * @param message the message to be sent to the observer
     */
    void update(String message);
}