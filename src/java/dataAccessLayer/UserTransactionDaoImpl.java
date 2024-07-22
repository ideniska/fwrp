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
import model.UserTransactionDTO;

public class UserTransactionDaoImpl {

    public UserTransactionDaoImpl() {
    }

    public List<UserTransactionDTO> getAllUserTransactions() throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<UserTransactionDTO> transactions = null;
        try {
            con = DataSource.getConnection();
            pstmt = con.prepareStatement(
                    "SELECT usertransaction_id, user_id, transaction_date FROM UserTransaction ORDER BY usertransaction_id");
            rs = pstmt.executeQuery();
            transactions = new ArrayList<UserTransactionDTO>();
            while (rs.next()) {
                UserTransactionDTO transaction = new UserTransactionDTO();
                transaction.setUserTransactionId(rs.getInt("usertransaction_id"));
                transaction.setUserId(rs.getInt("user_id"));
                transaction.setTransactionDate(rs.getDate("transaction_date"));
                transactions.add(transaction);
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
        return transactions;
    }

    public void addUserTransaction(UserTransactionDTO transaction) throws ClassNotFoundException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DataSource.getConnection();
            pstmt = con.prepareStatement(
                    "INSERT INTO UserTransaction (user_id, transaction_date) VALUES(?, ?)");
            pstmt.setInt(1, transaction.getUserId());
            pstmt.setDate(2, new java.sql.Date(transaction.getTransactionDate().getTime()));
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
