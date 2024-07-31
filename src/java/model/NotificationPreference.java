package model;

/**
 * Enumeration for user notification preferences.
 * Provides integer values for each preference and a method to retrieve the preference from its value.
 * 
 * author Berkay
 */
public enum NotificationPreference {
    NO(1),
    PHONE(2),
    EMAIL(3),
    BOTH(4);

    private final int value;

    /**
     * Constructor for NotificationPreference.
     *
     * @param value the integer value associated with the notification preference
     */
    NotificationPreference(int value) {
        this.value = value;
    }

    /**
     * Gets the integer value associated with the notification preference.
     *
     * @return the integer value
     */
    public int getValue() {
        return value;
    }

    /**
     * Retrieves the NotificationPreference associated with a given value.
     *
     * @param value the integer value of the notification preference
     * @return the NotificationPreference corresponding to the value
     * @throws IllegalArgumentException if the value does not correspond to any NotificationPreference
     */
    public static NotificationPreference fromValue(int value) {
        for (NotificationPreference preference : NotificationPreference.values()) {
            if (preference.getValue() == value) {
                return preference;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}