package notification;

/**
 * EmailObserver implements the Observer interface and represents an observer that receives notifications via email.
 * 
 * author Berkay
 */
public class EmailObserver implements Observer {
    private String email;

    /**
     * Constructs an EmailObserver with the specified email address.
     *
     * @param email the email address of the observer
     */
    public EmailObserver(String email) {
        this.email = email;
    }

    /**
     * Updates the observer with a message.
     * This method simulates sending an email notification by printing the message to the console.
     *
     * @param message the message to be sent to the observer
     */
    @Override
    public void update(String message) {
        System.out.println("Email to " + email + ": " + message);
    }
}