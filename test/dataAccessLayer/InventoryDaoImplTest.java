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
    

//    @BeforeClass
//    public static void setUpClass() throws SQLException, ClassNotFoundException {
//        try (Connection connection = DataSource.getConnection();
//            Statement stmt = connection.createStatement()) {
//            
//            connection.createStatement().execute("INSERT INTO Inventory (food_name, quantity, exp_date, surplus, price) "
//                + "VALUES ('Bread', 10, '2024-12-31', 1, 0.99)");
//        }
//    }

    @Test
    public void testGetAllInventory() throws Exception {
        InventoryDaoImpl instance = new InventoryDaoImpl();
        List<InventoryDTO> result = instance.getAllInventory();
        assertNotNull("Inventory list should not be null", result);
    }

    @Test
    public void testAddInventory() throws Exception {
        InventoryDaoImpl instance = new InventoryDaoImpl();
        InventoryDTO inventory = new InventoryDTO();
        inventory.setFoodName("Mango");
        inventory.setQuantity(20);
        inventory.setExpDate(java.sql.Date.valueOf("2024-12-31"));
        inventory.setSurplus(0);
        inventory.setPrice(1.99);
        instance.addInventory(inventory);
        
        
         try (Connection connection = DataSource.getConnection();
            Statement stmt = connection.createStatement()) {
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
        InventoryDaoImpl instance = new InventoryDaoImpl();
        List<InventoryDTO> result = instance.getSurplusInventory();
        try (Connection connection = DataSource.getConnection();
            Statement stmt = connection.createStatement()) {
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
        List<InventoryDTO> result = instance.getDiscountedInventory();
        assertNotNull(result);
    }

}