package model;

/**
 *
 * @author denissakhno
 */
public class TransactionItemDTO {
    private int transactionItemId;
    private int userTransactionId;
    private int foodId;
    private int quantity;
    private Double price;

    // Getters and Setters
    public int getTransactionItemId() {
        return transactionItemId;
    }

    public void setTransactionItemId(int transactionItemId) {
        this.transactionItemId = transactionItemId;
    }

    public int getUserTransactionId() {
        return userTransactionId;
    }

    public void setUserTransactionId(int userTransactionId) {
        this.userTransactionId = userTransactionId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}