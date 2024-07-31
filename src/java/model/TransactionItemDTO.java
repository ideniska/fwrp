package model;

/**
 * TransactionItemDTO represents an item in a user's transaction.
 * It contains details such as transaction item ID, user transaction ID, food ID, quantity, and price.
 * 
 * author Denis Sakhno
 */
public class TransactionItemDTO {
    private int transactionItemId;
    private int userTransactionId;
    private int foodId;
    private int quantity;
    private Double price;

    /**
     * Gets the transaction item ID.
     *
     * @return the transaction item ID
     */
    public int getTransactionItemId() {
        return transactionItemId;
    }

    /**
     * Sets the transaction item ID.
     *
     * @param transactionItemId the new transaction item ID
     */
    public void setTransactionItemId(int transactionItemId) {
        this.transactionItemId = transactionItemId;
    }

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
     * Gets the food ID.
     *
     * @return the food ID
     */
    public int getFoodId() {
        return foodId;
    }

    /**
     * Sets the food ID.
     *
     * @param foodId the new food ID
     */
    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    /**
     * Gets the quantity.
     *
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity.
     *
     * @param quantity the new quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the price.
     *
     * @return the price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Sets the price.
     *
     * @param price the new price
     */
    public void setPrice(Double price) {
        this.price = price;
    }
}