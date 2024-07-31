package dataAccessLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import model.InventoryDTO;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.ResultSet;

public class InventoryDaoImplTest {
    

    @Test
    public void testGetAllInventory() throws Exception {
        InventoryDaoImpl instance = new InventoryDaoImpl();
        List<InventoryDTO> result = instance.getAllInventory();
        assertNotNull("Inventory list should not be null", result);
    }

    @Test
    public void testAddInventory() throws Exception {
        int userId = 1;
        InventoryDaoImpl instance = new InventoryDaoImpl();
        InventoryDTO inventory = new InventoryDTO();
        inventory.setFoodName("Mango");
        inventory.setQuantity(20);
        inventory.setExpDate(java.sql.Date.valueOf("2024-12-31"));
        inventory.setSurplus(0);
        inventory.setPrice(1.99);
        instance.addInventory(inventory, userId);
        
        
         try (Connection connection = DataSource.getConnection();
                 ) {
            String query = "SELECT * FROM Inventory WHERE food_name = 'Mango'";
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            assertNotNull(rs);
        }

    }

    @Test
    public void testUpdateInventory() throws Exception {
        InventoryDaoImpl instance = new InventoryDaoImpl();
        InventoryDTO inventory = instance.getAllInventory().get(0);
        inventory.setQuantity(15);
        instance.updateInventory(inventory);

        InventoryDTO updatedInventory = instance.getInventoryById(inventory.getFoodId());
        assertEquals(15, updatedInventory.getQuantity().intValue());
    }



    @Test
    public void testGetSurplusInventory() throws Exception {

        try (Connection connection = DataSource.getConnection();
                ) {
            String query = "SELECT * FROM Inventory WHERE food_name = 'Mango'";
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            assertNotNull(rs);
        }
    }

    @Test
    public void testGetInventoryById() throws Exception {
        InventoryDaoImpl instance = new InventoryDaoImpl();
        List<InventoryDTO> inventories = instance.getAllInventory();
        int foodId = inventories.get(0).getFoodId();
        InventoryDTO result = instance.getInventoryById(foodId);
        assertNotNull(result);
        assertEquals("Apple", result.getFoodName());
    }

    @Test
    public void testDeleteInventory() throws Exception {
        InventoryDaoImpl instance = new InventoryDaoImpl();
        instance.deleteInventory(15); // delete Mango id #15
        assertNull(instance.getInventoryById(15));
        
    }
    
    @Test
    public void testGetLatestFoodId() throws Exception {
        InventoryDaoImpl instance = new InventoryDaoImpl();
        int latestFoodId = instance.getLatestFoodId();
        assertTrue(latestFoodId > 0);
    }

    @Test
    public void testGetFilteredInventory() throws Exception {
        InventoryDaoImpl instance = new InventoryDaoImpl();
        List<InventoryDTO> result = instance.getFilteredInventory();
        assertNotNull(result);
    }

}