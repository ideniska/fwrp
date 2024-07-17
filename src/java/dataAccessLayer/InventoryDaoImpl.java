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

    public List<InventoryDTO> getAllInventory() throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<InventoryDTO> inventoryList = null;
        try {
            DataSource ds = new DataSource();
            con = ds.createConnection();
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

    public void addInventory(InventoryDTO inventory) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            DataSource ds = new DataSource();
            con = ds.createConnection();
            pstmt = con.prepareStatement(
                    "INSERT INTO Inventory (food_name, quantity, exp_date, surplus, price) VALUES(?, ?, ?, ?, ?)");
            pstmt.setString(1, inventory.getFoodName());
            pstmt.setInt(2, inventory.getQuantity());
            pstmt.setDate(3, new java.sql.Date(inventory.getExpDate().getTime()));
            pstmt.setInt(4, inventory.getSurplus());
            pstmt.setInt(5, inventory.getPrice());
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
    public List<InventoryDTO> getSurplusInventory() throws SQLException {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    ArrayList<InventoryDTO> inventoryList = null;
    try {
        DataSource ds = new DataSource();
        con = ds.createConnection();
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

}
