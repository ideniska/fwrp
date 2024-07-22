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
import model.TransactionItemDTO;

public class TransactionItemDaoImpl {

    public TransactionItemDaoImpl() {
    }

    public List<TransactionItemDTO> getAllTransactionItems() throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<TransactionItemDTO> items = null;
        try {
            con = DataSource.getConnection();
            pstmt = con.prepareStatement(
                    "SELECT transactionitem_id, usertransaction_id, food_id, quantity, price FROM TransactionItem ORDER BY transactionitem_id");
            rs = pstmt.executeQuery();
            items = new ArrayList<TransactionItemDTO>();
            while (rs.next()) {
                TransactionItemDTO item = new TransactionItemDTO();
                item.setTransactionItemId(rs.getInt("transactionitem_id"));
                item.setUserTransactionId(rs.getInt("usertransaction_id"));
                item.setFoodId(rs.getInt("food_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPrice(rs.getInt("price"));
                items.add(item);
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
        return items;
    }

    public void addTransactionItem(TransactionItemDTO item) throws ClassNotFoundException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DataSource.getConnection();
            pstmt = con.prepareStatement(
                    "INSERT INTO TransactionItem (usertransaction_id, food_id, quantity, price) VALUES(?, ?, ?, ?)");
            pstmt.setInt(1, item.getUserTransactionId());
            pstmt.setInt(2, item.getFoodId());
            pstmt.setInt(3, item.getQuantity());
            pstmt.setInt(4, item.getPrice());
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
}
