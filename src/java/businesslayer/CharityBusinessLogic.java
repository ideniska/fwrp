/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package businesslayer;

import dataAccessLayer.InventoryDaoImpl;
import dataAccessLayer.TransactionItemDaoImpl;
import dataAccessLayer.UserTransactionDaoImpl;
import model.InventoryDTO;
import java.sql.SQLException;
import java.util.List;
import model.TransactionItemDTO;
import model.UserTransactionDTO;

/**
 * Business logic layer for managing charity operations.
 * Provides methods to perform operations on charity inventory items.
 * 
 * author dmlop
 */
public class CharityBusinessLogic {
    private InventoryDaoImpl inventoryDao = null;
    private TransactionItemDaoImpl transactionItemDao = null;
    private UserTransactionDaoImpl userTransactionDao = null;

    public CharityBusinessLogic() {
        inventoryDao = new InventoryDaoImpl();
        transactionItemDao = new TransactionItemDaoImpl();
        userTransactionDao = new UserTransactionDaoImpl();
    }

    public List<InventoryDTO> getCharityInventory() throws SQLException, ClassNotFoundException {
        return inventoryDao.getCharityInventory();
    }

    public InventoryDTO getInventoryById(int foodId) throws SQLException, ClassNotFoundException {
        return inventoryDao.getInventoryById(foodId);
    }

    public void updateInventory(InventoryDTO inventoryItem) throws SQLException, ClassNotFoundException {
        inventoryDao.updateInventory(inventoryItem);
    }

    public void addTransactionItem(TransactionItemDTO transactionItem) throws SQLException, ClassNotFoundException {
        transactionItemDao.addTransactionItem(transactionItem);
    }

    public void addUserTransaction(UserTransactionDTO userTransaction) throws SQLException, ClassNotFoundException {
        userTransactionDao.addUserTransaction(userTransaction);
    }
}