package notification;

/**
 * SMSObserver implements the Observer interface and represents an observer that receives notifications via SMS.
 * 
 * author Berkay
 */
public class SMSObserver implements Observer {
    private String phoneNumber;

    /**
     * Constructs an SMSObserver with the specified phone number.
     *
     * @param phoneNumber the phone number of the observer
     */
    public SMSObserver(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Updates the observer with a message.
     * This method simulates sending an SMS notification by printing the message to the console.
     *
     * @param message the message to be sent to the observer
     */
    @Override
    public void update(String message) {
        System.out.println("SMS to " + phoneNumber + ": " + message);
    }
}