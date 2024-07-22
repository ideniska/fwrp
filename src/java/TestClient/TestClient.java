/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TestClient;



import dataAccessLayer.InventoryDaoImpl;
import model.InventoryDTO;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author denissakhno
 */

public class TestClient {
    public static void main(String[] args) throws ClassNotFoundException {
        InventoryDaoImpl inventoryDao = new InventoryDaoImpl();
        
        // Adding a new inventory item
        InventoryDTO newInventory = new InventoryDTO();
        newInventory.setFoodName("Apple");
        newInventory.setQuantity(100);
        newInventory.setExpDate(new Date()); // Set current date for simplicity
        newInventory.setSurplus(10);
        newInventory.setPrice(50.0);

        inventoryDao.addInventory(newInventory);
        System.out.println("New inventory item added successfully!");

        // Retrieving all inventory items
        try {
            List<InventoryDTO> inventoryList = inventoryDao.getAllInventory();
            System.out.println("Inventory List:");
            for (InventoryDTO inventory : inventoryList) {
                System.out.println("Food ID: " + inventory.getFoodId());
                System.out.println("Food Name: " + inventory.getFoodName());
                System.out.println("Quantity: " + inventory.getQuantity());
                System.out.println("Expiration Date: " + inventory.getExpDate());
                System.out.println("Surplus: " + inventory.getSurplus());
                System.out.println("Price: " + inventory.getPrice());
                System.out.println("-----------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}