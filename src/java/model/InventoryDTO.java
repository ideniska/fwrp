package model;

import java.util.Date;

/**
 * InventoryDTO represents an item in the inventory.
 * It contains details such as food ID, name, quantity, expiration date, surplus status, price, user ID, food preference, and location.
 * 
 */
public class InventoryDTO {
    private Integer foodId;
    private String foodName;
    private Integer quantity;
    private Date expDate;
    private Integer surplus;
    private Double price;
    private int userId;
    private String foodPreference;
    private String location;

    /**
     * Gets the food ID.
     *
     * @return the food ID
     */
    public Integer getFoodId() {
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
     * Gets the food name.
     *
     * @return the food name
     */
    public String getFoodName() {
        return foodName;
    }

    /**
     * Sets the food name.
     *
     * @param foodName the new food name
     */
    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    /**
     * Gets the quantity.
     *
     * @return the quantity
     */
    public Integer getQuantity() {
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
     * Gets the expiration date.
     *
     * @return the expiration date
     */
    public Date getExpDate() {
        return expDate;
    }

    /**
     * Sets the expiration date.
     *
     * @param expDate the new expiration date
     */
    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    /**
     * Gets the surplus status.
     *
     * @return the surplus status
     */
    public Integer getSurplus() {
        return surplus;
    }

    /**
     * Sets the surplus status.
     *
     * @param surplus the new surplus status
     */
    public void setSurplus(int surplus) {
        this.surplus = surplus;
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
}