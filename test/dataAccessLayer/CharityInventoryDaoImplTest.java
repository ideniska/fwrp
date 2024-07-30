package dataAccessLayer;

import model.InventoryDTO;
import org.junit.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

public class CharityInventoryDaoImplTest {

    private CharityInventoryDaoImpl charityInventoryDao;

//    // Initialize test database
//    @BeforeClass
//    public static void setUpClass() throws ClassNotFoundException, SQLException {
//        try (Connection connection = DataSource.getConnection();
//             Statement stmt = connection.createStatement()) {
//         
//            stmt.execute("CREATE DATABASE IF NOT EXISTS testDB");
//            
//            stmt.execute("USE testDB");
//            
//
//            String createTableSQL = "CREATE TABLE Inventory (" +
//                    "food_id INT PRIMARY KEY, " +
//                    "food_name VARCHAR(255), " +
//                    "quantity INT, " +
//                    "exp_date DATE, " +
//                    "price DOUBLE, " +
//                    "surplus BOOLEAN)";
//            stmt.execute(createTableSQL);
//
//            String insertDataSQL = "INSERT INTO Inventory (food_id, food_name, quantity, exp_date, price, surplus) VALUES " +
//                    "(1, 'Apple', 100, CURRENT_DATE + INTERVAL '10' DAY, 0.50, true), " +
//                    "(2, 'Banana', 50, CURRENT_DATE + INTERVAL '5' DAY, 0.30, false)";
//            stmt.execute(insertDataSQL);
//        }
//    }
//
//    
//    @AfterClass
//    public static void tearDownClass() throws SQLException, ClassNotFoundException {
//        try (Connection connection = DataSource.getConnection();
//            Statement stmt = connection.createStatement()) {
//            stmt.execute("USE testDB");
//            stmt.execute("DROP TABLE Inventory");
//        }
//    }

    @Before
    public void setUp() {
        charityInventoryDao = new CharityInventoryDaoImpl();
    }

    @Test
    public void testGetCharityInventory() throws SQLException, ClassNotFoundException {
        List<InventoryDTO> inventoryList = charityInventoryDao.getCharityInventory();
        assertNotNull("Inventory list should not be null", inventoryList);
    }
    
    @Test
    public void testUpdateInventory() throws SQLException, ClassNotFoundException, ParseException {

        String query = "UPDATE Inventory SET food_name = ?, quantity = ?, exp_date = ?, surplus = ?, price = ? WHERE food_id = ?";
        int foodId = 1;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parsedDate = dateFormat.parse("2024-08-15");
        java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
        
        // Set quantity of Apples to 50
        try (Connection con = DataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query);) {

            pstmt.setString(1, "Apple");
            pstmt.setInt(2, 50);
            pstmt.setDate(3, sqlDate);
            pstmt.setInt(4, 1);
            pstmt.setDouble(5, 123.11);
            pstmt.setInt(6, foodId);
            pstmt.executeUpdate();
        }
        

        int claimQuantity = 20;


        // Update inventory
        charityInventoryDao.updateInventory(foodId, claimQuantity);

        List<InventoryDTO> updatedInventoryList = charityInventoryDao.getCharityInventory();

        // Find Apple in the updated inventory list
        InventoryDTO apple = updatedInventoryList.stream()
                                                  .filter(item -> item.getFoodId() == foodId)
                                                  .findFirst()
                                                  .orElse(null);

        // Assert that Apple is still in the inventory
        assertNotNull("Apple should be in inventory", apple);

        // Expected quantity after update (original 50 - claimed 20 = 30)
        int expectedQuantity = 30;
        assertEquals("Banana quantity should be " + expectedQuantity, expectedQuantity, apple.getQuantity().intValue());
    }
}
