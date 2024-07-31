package model;

/**
 * UserDTO represents a user in the system.
 * It contains details such as user ID, first name, last name, phone, address, email, password, user type, location, food preference, notifications, organization name, and credit.
 * 
 * author Denis Sakhno
 */
public class UserDTO {
    private int userId;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String email;
    private String password;
    private int userType;
    private String location;
    private String foodPreference;
    private Integer notifications;
    private String orgName;
    private double credit;

    /**
     * Gets the user ID.
     *
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user ID.
     *
     * @param userId the new user ID
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     *
     * @param firstName the new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name.
     *
     * @param lastName the new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the phone number.
     *
     * @return the phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number.
     *
     * @param phone the new phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address.
     *
     * @param address the new address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email the new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the user type.
     *
     * @return the user type
     */
    public int getUserType() {
        return userType;
    }

    /**
     * Sets the user type.
     *
     * @param userType the new user type
     */
    public void setUserType(int userType) {
        this.userType = userType;
    }

    /**
     * Gets the location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location.
     *
     * @param location the new location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the food preference.
     *
     * @return the food preference
     */
    public String getFoodPreference() {
        return foodPreference;
    }

    /**
     * Sets the food preference.
     *
     * @param foodPreference the new food preference
     */
    public void setFoodPreference(String foodPreference) {
        this.foodPreference = foodPreference;
    }

    /**
     * Gets the notification preference.
     *
     * @return the notification preference
     */
    public Integer getNotifications() {
        return notifications;
    }

    /**
     * Sets the notification preference.
     *
     * @param notifications the new notification preference
     */
    public void setNotifications(Integer notifications) {
        this.notifications = notifications;
    }

    /**
     * Gets the organization name.
     *
     * @return the organization name
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * Sets the organization name.
     *
     * @param orgName the new organization name
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * Gets the credit.
     *
     * @return the credit
     */
    public double getCredit() {
        return credit;
    }

    /**
     * Sets the credit.
     *
     * @param credit the new credit
     */
    public void setCredit(double credit) {
        this.credit = credit;
    }
}