package businesslayer;

import java.util.List;
import java.sql.SQLException;
import model.InventoryDTO;
import dataAccessLayer.InventoryDaoImpl;

/**
 * Business logic layer for managing consumer operations.
 * Provides methods to perform operations on inventory items.
 * 
 * author Yuchen Wang
 */
public class ConsumerBusinessLogic {
    private InventoryDaoImpl inventoryDao = null;
    
    public ConsumerBusinessLogic() {
        inventoryDao = new InventoryDaoImpl();
    }

    public List<InventoryDTO> getAllInventory() throws SQLException, ClassNotFoundException {
        return inventoryDao.getAllInventory();
    }

    public List<InventoryDTO> getFilteredInventory() throws SQLException, ClassNotFoundException {
        return inventoryDao.getFilteredInventory();
    }

    public boolean updateInventory(int foodId, int quantity) throws SQLException, ClassNotFoundException {
        return inventoryDao.updateInventory(foodId, quantity);
    }

    public void logPurchase(int userId, int foodId, int quantity, double price) throws SQLException, ClassNotFoundException {
        inventoryDao.logPurchase(userId, foodId, quantity, price);
    }
}