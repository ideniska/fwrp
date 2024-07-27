package businesslayer;

import java.util.List;
import java.sql.SQLException;
import model.InventoryDTO;
import dataAccessLayer.InventoryDaoImpl;

/**
 * Business logic layer for managing retailer inventory.
 * Provides methods to perform CRUD operations on inventory items.
 * 
 * author denissakhno
 */
public class RetailerBusinessLogic {
    private InventoryDaoImpl inventoryDao = null;
    
    public RetailerBusinessLogic() {
        inventoryDao = new InventoryDaoImpl();
    }

    /**
     * Retrieves all inventory items.
     * 
     * @return a list of InventoryDTO objects
     * @throws SQLException if a database access error occurs
     */
    public List<InventoryDTO> getAllInventory() throws SQLException, ClassNotFoundException {
        return inventoryDao.getAllInventory();
    }

    public List<InventoryDTO> getInventoryByUserId(int userId) throws SQLException, ClassNotFoundException {
        return inventoryDao.getInventoryByUserId(userId);
    }
        
    /**
     * Adds a new inventory item.
     * 
     * @param inventoryItem the InventoryDTO object to be added
     */
    public void addInventory(InventoryDTO inventoryItem, int userId) throws ClassNotFoundException {
        inventoryDao.addInventory(inventoryItem, userId);
    }

    /**
     * Updates an existing inventory item.
     * 
     * @param inventoryItem the InventoryDTO object containing updated information
     */
    public void updateInventory(InventoryDTO inventoryItem) throws ClassNotFoundException {
        inventoryDao.updateInventory(inventoryItem);
    }

    /**
     * Deletes an inventory item.
     * 
     * @param foodId the ID of the inventory item to be deleted
     */
    public void deleteInventory(int foodId) throws ClassNotFoundException {
        inventoryDao.deleteInventory(foodId);
    }

    /**
     * Retrieves an inventory item by its ID.
     * 
     * @param foodId the ID of the inventory item to be retrieved
     * @return the InventoryDTO object representing the inventory item
     * @throws SQLException if a database access error occurs
     */
    public InventoryDTO getInventoryById(int foodId) throws SQLException, ClassNotFoundException {
        return inventoryDao.getInventoryById(foodId);
    }
    
    /**
     * Retrieves the latest food ID.
     * 
     * @return the latest food ID
     * @throws SQLException if a database access error occurs
     */
    public int getLatestFoodId() throws SQLException, ClassNotFoundException {
        return inventoryDao.getLatestFoodId();
    }
}