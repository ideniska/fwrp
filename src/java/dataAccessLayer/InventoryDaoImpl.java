/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataAccessLayer;

import java.util.List;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.InventoryDTO;

/**
 *
 * @author denissakhno
 */
public class InventoryDaoImpl {

    public InventoryDaoImpl() {
    }

    public List<InventoryDTO> getAllInventory() throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<InventoryDTO> inventoryList = null;
        try {
            con = DataSource.getConnection();
            pstmt = con.prepareStatement(
                    "SELECT food_id, food_name, quantity, exp_date, surplus, price FROM Inventory ORDER BY food_id");
            rs = pstmt.executeQuery();
            inventoryList = new ArrayList<InventoryDTO>();
            while (rs.next()) {
                InventoryDTO inventory = new InventoryDTO();
                inventory.setFoodId(rs.getInt("food_id"));
                inventory.setFoodName(rs.getString("food_name"));
                inventory.setQuantity(rs.getInt("quantity"));
                inventory.setExpDate(rs.getDate("exp_date"));
                inventory.setSurplus(rs.getInt("surplus"));
                inventory.setPrice(rs.getInt("price"));
                inventoryList.add(inventory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return inventoryList;
    }

    public void addInventory(InventoryDTO inventory) throws ClassNotFoundException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DataSource.getConnection();
            pstmt = con.prepareStatement(
                    "INSERT INTO Inventory (food_name, quantity, exp_date, surplus, price) VALUES(?, ?, ?, ?, ?)");
            pstmt.setString(1, inventory.getFoodName());
            pstmt.setInt(2, inventory.getQuantity());
            pstmt.setDate(3, new java.sql.Date(inventory.getExpDate().getTime()));
            pstmt.setInt(4, inventory.getSurplus());
            pstmt.setDouble(5, inventory.getPrice());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    public List<InventoryDTO> getSurplusInventory() throws SQLException, ClassNotFoundException {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    ArrayList<InventoryDTO> inventoryList = null;
    try {
        con = DataSource.getConnection();
        pstmt = con.prepareStatement("SELECT food_id, food_name, quantity, exp_date, price FROM Inventory WHERE surplus = 1");
        rs = pstmt.executeQuery();
        inventoryList = new ArrayList<InventoryDTO>();
        while (rs.next()) {
            InventoryDTO inventory = new InventoryDTO();
            inventory.setFoodId(rs.getInt("food_id"));
            inventory.setFoodName(rs.getString("food_name"));
            inventory.setQuantity(rs.getInt("quantity"));
            inventory.setExpDate(rs.getDate("exp_date"));
            inventory.setPrice(rs.getInt("price"));
            inventoryList.add(inventory);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw e;
    } finally {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    return inventoryList;
}
//================================== I update the method from here(Yuchen Wang)==============================================
 public List<InventoryDTO> getFilteredInventory() throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<InventoryDTO> inventoryList = null;
        try {
            con = DataSource.getConnection();
            pstmt = con.prepareStatement("SELECT food_id, food_name, quantity, exp_date, price FROM Inventory WHERE exp_date BETWEEN DATE_ADD(NOW(), INTERVAL 7 DAY) AND DATE_ADD(NOW(), INTERVAL 14 DAY)");
            rs = pstmt.executeQuery();
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
        } finally {
            closeResources(rs, pstmt, con);
        }
        return inventoryList;
    }

    public boolean updateInventory(int foodId, int quantity) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DataSource.getConnection();
            pstmt = con.prepareStatement("UPDATE Inventory SET quantity = quantity - ? WHERE food_id = ? AND quantity >= ?");
            pstmt.setInt(1, quantity);
            pstmt.setInt(2, foodId);
            pstmt.setInt(3, quantity);
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } finally {
            closeResources(null, pstmt, con);
        }
    }

    public void logPurchase(int userId, int foodId, int quantity, double price) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement transactionStmt = null;
        PreparedStatement itemStmt = null;
        ResultSet generatedKeys = null;
        try {
            con = DataSource.getConnection();
            transactionStmt = con.prepareStatement("INSERT INTO UserTransaction (user_id, transaction_date) VALUES (?, NOW())", PreparedStatement.RETURN_GENERATED_KEYS);
            transactionStmt.setInt(1, userId);
            transactionStmt.executeUpdate();
            generatedKeys = transactionStmt.getGeneratedKeys();
            int transactionId = 0;
            if (generatedKeys.next()) {
                transactionId = generatedKeys.getInt(1);
            }

            itemStmt = con.prepareStatement("INSERT INTO TransactionItem (usertransaction_id, food_id, quantity, price) VALUES (?, ?, ?, ?)");
            itemStmt.setInt(1, transactionId);
            itemStmt.setInt(2, foodId);
            itemStmt.setInt(3, quantity);
            itemStmt.setDouble(4, price);
            itemStmt.executeUpdate();
        } finally {
            closeResources(generatedKeys, transactionStmt, null);
            closeResources(null, itemStmt, con);
        }
    }

    private void closeResources(ResultSet rs, PreparedStatement pstmt, Connection con) {
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
}