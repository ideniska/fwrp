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
    public List<InventoryDTO> getAllInventory() throws SQLException {
        return inventoryDao.getAllInventory();
    }

    /**
     * Adds a new inventory item.
     * 
     * @param inventoryItem the InventoryDTO object to be added
     */
    public void addInventory(InventoryDTO inventoryItem) {
        inventoryDao.addInventory(inventoryItem);
    }

    /**
     * Updates an existing inventory item.
     * 
     * @param inventoryItem the InventoryDTO object containing updated information
     */
    public void updateInventory(InventoryDTO inventoryItem) {
        inventoryDao.updateInventory(inventoryItem);
    }

    /**
     * Deletes an inventory item.
     * 
     * @param foodId the ID of the inventory item to be deleted
     */
    public void deleteInventory(int foodId) {
        inventoryDao.deleteInventory(foodId);
    }

    /**
     * Retrieves an inventory item by its ID.
     * 
     * @param foodId the ID of the inventory item to be retrieved
     * @return the InventoryDTO object representing the inventory item
     * @throws SQLException if a database access error occurs
     */
    public InventoryDTO getInventoryById(int foodId) throws SQLException {
        return inventoryDao.getInventoryById(foodId);
    }
}