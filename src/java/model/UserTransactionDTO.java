/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author denissakhno
 */
import java.util.Date;

public class UserTransactionDTO {
    private int userTransactionId;
    private int userId;
    private Date transactionDate;

    // Getters and Setters
    public int getUserTransactionId() {
        return userTransactionId;
    }

    public void setUserTransactionId(int userTransactionId) {
        this.userTransactionId = userTransactionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}
