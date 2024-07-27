package dataAccessLayer;

import java.util.List;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.InventoryDTO;

/**
 * Data Access Object implementation for the Inventory.
 * Provides methods to perform CRUD operations on the Inventory table.
 *
 * author denissakhno
 */
public class InventoryDaoImpl {

    public InventoryDaoImpl() {
    }

    /**
     * Retrieves all inventory items from the database.
     *
     * @return a list of InventoryDTO objects
     * @throws SQLException if a database access error occurs
     */
    public List<InventoryDTO> getAllInventory() throws SQLException, ClassNotFoundException {
        List<InventoryDTO> inventoryList = new ArrayList<>();
        String query = "SELECT food_id, food_name, quantity, exp_date, surplus, price FROM Inventory ORDER BY food_id";

        try (Connection con = DataSource.getConnection();
                PreparedStatement pstmt = con.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                InventoryDTO inventory = new InventoryDTO();
                inventory.setFoodId(rs.getInt("food_id"));
                inventory.setFoodName(rs.getString("food_name"));
                inventory.setQuantity(rs.getInt("quantity"));
                inventory.setExpDate(rs.getDate("exp_date"));
                inventory.setSurplus(rs.getInt("surplus"));
                inventory.setPrice(rs.getDouble("price"));
                inventoryList.add(inventory);
            }
        }
        return inventoryList;
    }

    public List<InventoryDTO> getInventoryByUserId(int userId) throws SQLException, ClassNotFoundException {
    List<InventoryDTO> inventoryList = new ArrayList<>();
    String query = "SELECT food_id, food_name, quantity, exp_date, surplus, price FROM Inventory WHERE user_id = ?";

    try (Connection con = DataSource.getConnection();
         PreparedStatement pstmt = con.prepareStatement(query)) {
        pstmt.setInt(1, userId);
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                InventoryDTO inventory = new InventoryDTO();
                inventory.setFoodId(rs.getInt("food_id"));
                inventory.setFoodName(rs.getString("food_name"));
                inventory.setQuantity(rs.getInt("quantity"));
                inventory.setExpDate(rs.getDate("exp_date"));
                inventory.setSurplus(rs.getInt("surplus"));
                inventory.setPrice(rs.getDouble("price"));
                inventoryList.add(inventory);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw e;
    }
    return inventoryList;
}

        
    /**
     * Adds a new inventory item to the database.
     *
     * @param inventory the InventoryDTO object to be added
     */
    public void addInventory(InventoryDTO inventory, int userId) throws ClassNotFoundException {
        String query = "INSERT INTO Inventory (food_name, quantity, exp_date, surplus, price, user_id) VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection con = DataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, inventory.getFoodName());
            pstmt.setInt(2, inventory.getQuantity());
            pstmt.setDate(3, new java.sql.Date(inventory.getExpDate().getTime()));
            pstmt.setInt(4, inventory.getSurplus());
            pstmt.setDouble(5, inventory.getPrice());
            pstmt.setInt(6, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Updates an existing inventory item in the database.
     *
     * @param inventory the InventoryDTO object containing updated information
     */
    public void updateInventory(InventoryDTO inventory) throws ClassNotFoundException {
        String query = "UPDATE Inventory SET food_name = ?, quantity = ?, exp_date = ?, surplus = ?, price = ? WHERE food_id = ?";

        try (Connection con = DataSource.getConnection();
                PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, inventory.getFoodName());
            pstmt.setInt(2, inventory.getQuantity());
            pstmt.setDate(3, new java.sql.Date(inventory.getExpDate().getTime()));
            pstmt.setInt(4, inventory.getSurplus());
            pstmt.setDouble(5, inventory.getPrice());
            pstmt.setInt(6, inventory.getFoodId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes an inventory item from the database.
     *
     * @param foodId the ID of the inventory item to be deleted
     */
    public void deleteInventory(int foodId) throws ClassNotFoundException {
        String query = "DELETE FROM Inventory WHERE food_id = ?";

        try (Connection con = DataSource.getConnection();
                PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setInt(1, foodId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all surplus inventory items from the database.
     *
     * @return a list of InventoryDTO objects representing surplus items
     * @throws SQLException if a database access error occurs
     */
    public List<InventoryDTO> getSurplusInventory() throws SQLException, ClassNotFoundException {
        List<InventoryDTO> inventoryList = new ArrayList<>();
        String query = "SELECT food_id, food_name, quantity, exp_date, price FROM Inventory WHERE surplus = 1";

        try (Connection con = DataSource.getConnection();
                PreparedStatement pstmt = con.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                InventoryDTO inventory = new InventoryDTO();
                inventory.setFoodId(rs.getInt("food_id"));
                inventory.setFoodName(rs.getString("food_name"));
                inventory.setQuantity(rs.getInt("quantity"));
                inventory.setExpDate(rs.getDate("exp_date"));
                inventory.setPrice(rs.getDouble("price"));
                inventoryList.add(inventory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return inventoryList;
    }

    /**
     * Retrieves an inventory item by its ID.
     *
     * @param foodId the ID of the inventory item to be retrieved
     * @return the InventoryDTO object representing the inventory item
     * @throws SQLException if a database access error occurs
     */
    public InventoryDTO getInventoryById(int foodId) throws SQLException, ClassNotFoundException {
        InventoryDTO inventory = null;
        String query = "SELECT food_id, food_name, quantity, exp_date, surplus, price FROM Inventory WHERE food_id = ?";

        try (Connection con = DataSource.getConnection();
                PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setInt(1, foodId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    inventory = new InventoryDTO();
                    inventory.setFoodId(rs.getInt("food_id"));
                    inventory.setFoodName(rs.getString("food_name"));
                    inventory.setQuantity(rs.getInt("quantity"));
                    inventory.setExpDate(rs.getDate("exp_date"));
                    inventory.setSurplus(rs.getInt("surplus"));
                    inventory.setPrice(rs.getDouble("price"));
                }
            }
        }
        return inventory;
    }

    /**
     * Retrieves the latest food ID from the database.
     *
     * @return the latest food ID
     * @throws SQLException if a database access error occurs
     */
    public int getLatestFoodId() throws SQLException, ClassNotFoundException {
        String query = "SELECT MAX(food_id) AS latestFoodId FROM Inventory";
        int latestFoodId = 0;

        try (Connection con = DataSource.getConnection();
                PreparedStatement pstmt = con.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                latestFoodId = rs.getInt("latestFoodId");
            }
        }
        return latestFoodId;
    }

    // TODO CHANGE METHOD NAME TO MORE UNDERSTANDABLE
    public List<InventoryDTO> getFilteredInventory() throws SQLException, ClassNotFoundException {
        ArrayList<InventoryDTO> inventoryList = null;
        try (Connection con = DataSource.getConnection();
                PreparedStatement pstmt = con.prepareStatement(
                        "SELECT food_id, food_name, quantity, exp_date, price FROM Inventory WHERE exp_date BETWEEN DATE_ADD(NOW(), INTERVAL 7 DAY) AND DATE_ADD(NOW(), INTERVAL 14 DAY)");
                ResultSet rs = pstmt.executeQuery();) {

            inventoryList = new ArrayList<>();
            while (rs.next()) {
                InventoryDTO inventory = new InventoryDTO();
                inventory.setFoodId(rs.getInt("food_id"));
                inventory.setFoodName(rs.getString("food_name"));
                inventory.setQuantity(rs.getInt("quantity"));
                inventory.setExpDate(rs.getDate("exp_date"));
                inventory.setPrice(rs.getDouble("price"));
                inventoryList.add(inventory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return inventoryList;
    }
    
    // TODO YUCHEN: UPDATE THIS METHOD TO ACCEPT INVENTORY DTO not its fields -> DAO works with DTOs
    public void logPurchase(int userId, int foodId, int quantity, double price) throws SQLException, ClassNotFoundException {
        String transactionQuery = "INSERT INTO UserTransaction (user_id, transaction_date) VALUES (?, NOW())";
        String itemQuery = "INSERT INTO TransactionItem (usertransaction_id, food_id, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection con = DataSource.getConnection();
             PreparedStatement transactionStmt = con.prepareStatement(transactionQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {

            transactionStmt.setInt(1, userId);
            transactionStmt.executeUpdate();

            try (ResultSet generatedKeys = transactionStmt.getGeneratedKeys()) {
                int transactionId = 0;
                if (generatedKeys.next()) {
                    transactionId = generatedKeys.getInt(1);
                }

                try (PreparedStatement itemStmt = con.prepareStatement(itemQuery)) {
                    itemStmt.setInt(1, transactionId);
                    itemStmt.setInt(2, foodId);
                    itemStmt.setInt(3, quantity);
                    itemStmt.setDouble(4, price);
                    itemStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

}