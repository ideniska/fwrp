package dataAccessLayer;

import model.InventoryDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of data access operations for charity inventory.
 */
public class CharityInventoryDaoImpl {

    public List<InventoryDTO> getCharityInventory() throws SQLException, ClassNotFoundException {
        List<InventoryDTO> inventoryList = new ArrayList<>();
        String query = "SELECT food_id, food_name, quantity, exp_date, price FROM Inventory " +
                       "WHERE surplus = 1 OR exp_date BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 7 DAY)";

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

    public void updateInventory(int foodId, int claimQuantity) throws SQLException, ClassNotFoundException {
        String query = "UPDATE Inventory SET quantity = quantity - ? WHERE food_id = ? AND quantity >= ?";

        try (Connection con = DataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setInt(1, claimQuantity);
            pstmt.setInt(2, foodId);
            pstmt.setInt(3, claimQuantity);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}