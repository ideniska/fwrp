package businesslayer;

import java.util.List;
import java.sql.SQLException;
import model.InventoryDTO;
import dataAccessLayer.InventoryDaoImpl;
import dataAccessLayer.TransactionItemDaoImpl;
import dataAccessLayer.UserDaoImpl;
import dataAccessLayer.UserTransactionDaoImpl;
import model.TransactionItemDTO;
import model.UserDTO;
import model.UserTransactionDTO;

/**
 * Business logic layer for managing consumer operations.
 * Provides methods to perform operations on inventory items.
 * 
 * author Yuchen Wang
 */
public class ConsumerBusinessLogic {
    private InventoryDaoImpl inventoryDao = null;
    private TransactionItemDaoImpl transactionItemDao = null;
    private UserTransactionDaoImpl userTransactionDao = null;
    private UserDaoImpl userDao = null;

     /**
     * Constructor to initialize the data access objects.
     */
    public ConsumerBusinessLogic() {
        inventoryDao = new InventoryDaoImpl();
        transactionItemDao = new TransactionItemDaoImpl();
        userTransactionDao = new UserTransactionDaoImpl();
        userDao = new UserDaoImpl();
    }

     /**
     * Retrieves all inventory items.
     *
     * @return a list of InventoryDTO objects
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the JDBC driver class is not found
     */
    public List<InventoryDTO> getAllInventory() throws SQLException, ClassNotFoundException {
        return inventoryDao.getAllInventory();
    }

    /**
     * Retrieves filtered inventory items based on specific criteria.
     *
     * @return a list of InventoryDTO objects
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the JDBC driver class is not found
     */
    public List<InventoryDTO> getFilteredInventory() throws SQLException, ClassNotFoundException {
        return inventoryDao.getFilteredInventory();
    }

     /**
     * Retrieves an inventory item by its ID.
     *
     * @param foodId the ID of the inventory item
     * @return the InventoryDTO object representing the inventory item
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the JDBC driver class is not found
     */
    public InventoryDTO getInventoryById(int foodId) throws SQLException, ClassNotFoundException {
        return inventoryDao.getInventoryById(foodId);
    }

      /**
     * Updates an existing inventory item.
     *
     * @param inventoryItem the InventoryDTO object containing updated information
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the JDBC driver class is not found
     */
    public void updateInventory(InventoryDTO inventoryItem) throws SQLException, ClassNotFoundException {
        inventoryDao.updateInventory(inventoryItem);
    }

    /**
     * Adds a new transaction item.
     *
     * @param transactionItem the TransactionItemDTO object to be added
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the JDBC driver class is not found
     */
    public void addTransactionItem(TransactionItemDTO transactionItem) throws SQLException, ClassNotFoundException {
        transactionItemDao.addTransactionItem(transactionItem);
    }

    /**
     * Adds a new userTransaction.
     *
     * @param userTransaction the UserTransactionDTO object to be added
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the JDBC driver class is not found
     */
    public void addUserTransaction(UserTransactionDTO userTransaction)throws SQLException, ClassNotFoundException {
        userTransactionDao.addUserTransaction(userTransaction);
    }   
    
    public UserDTO getUserCreditById(int userId) throws SQLException, ClassNotFoundException {
        return userDao.getUserCreditById(userId);
    }

    public void updateUserCredit(UserDTO user) throws SQLException, ClassNotFoundException {
        userDao.updateUserCredit(user);
    }
}