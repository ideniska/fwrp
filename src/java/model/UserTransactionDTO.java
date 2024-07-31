package model;

import java.util.Date;

/**
 * UserTransactionDTO represents a transaction made by a user.
 * It contains details such as user transaction ID, user ID, and transaction date.
 * 
 * author Denis Sakhno
 */
public class UserTransactionDTO {
    private int userTransactionId;
    private int userId;
    private Date transactionDate;

    /**
     * Gets the user transaction ID.
     *
     * @return the user transaction ID
     */
    public int getUserTransactionId() {
        return userTransactionId;
    }

    /**
     * Sets the user transaction ID.
     *
     * @param userTransactionId the new user transaction ID
     */
    public void setUserTransactionId(int userTransactionId) {
        this.userTransactionId = userTransactionId;
    }

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
     * Gets the transaction date.
     *
     * @return the transaction date
     */
    public Date getTransactionDate() {
        return transactionDate;
    }

    /**
     * Sets the transaction date.
     *
     * @param transactionDate the new transaction date
     */
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}