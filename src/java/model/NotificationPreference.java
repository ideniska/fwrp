/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author berka
 */
public enum NotificationPreference {
    NO(1),
    PHONE(2),
    EMAIL(3),
    BOTH(4);

    private final int value;

    NotificationPreference(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static NotificationPreference fromValue(int value) {
        for (NotificationPreference preference : NotificationPreference.values()) {
            if (preference.getValue() == value) {
                return preference;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
