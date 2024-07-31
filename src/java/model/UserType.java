package model;

/**
 * Enumeration for user types.
 * Provides integer values for each user type and a method to retrieve the user type from its value.
 * 
 * author Berkay
 */
public enum UserType {
    CUSTOMER(1),
    CHARITY(2),
    RETAILER(3);

    private final int value;

    /**
     * Constructor for UserType.
     *
     * @param value the integer value associated with the user type
     */
    UserType(int value) {
        this.value = value;
    }

    /**
     * Gets the integer value associated with the user type.
     *
     * @return the integer value
     */
    public int getValue() {
        return value;
    }

    /**
     * Retrieves the UserType associated with a given value.
     *
     * @param value the integer value of the user type
     * @return the UserType corresponding to the value
     * @throws IllegalArgumentException if the value does not correspond to any UserType
     */
    public static UserType fromValue(int value) {
        for (UserType type : UserType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}