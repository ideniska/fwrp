/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataAccessLayer;

import model.InventoryDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dmlop
 */
public class CharityInventoryDaoImpl {

    public List<InventoryDTO> getCharityInventory() throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<InventoryDTO> inventoryList = new ArrayList<>();
        try {
            con = DataSource.getConnection();
            pstmt = con.prepareStatement(
                    "SELECT food_id, food_name, quantity, exp_date, price FROM Inventory " +
                    "WHERE surplus = 1 OR exp_date BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 7 DAY)");
            rs = pstmt.executeQuery();

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
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        return inventoryList;
    }

    public void updateInventory(int foodId, int claimQuantity) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DataSource.getConnection();
            pstmt = con.prepareStatement("UPDATE Inventory SET quantity = quantity - ? WHERE food_id = ? AND quantity >= ?");
            pstmt.setInt(1, claimQuantity);
            pstmt.setInt(2, foodId);
            pstmt.setInt(3, claimQuantity);
            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
}