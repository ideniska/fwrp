package dataAccessLayer;

import model.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl {

    public UserDaoImpl() {
    }

    public UserDTO getUserById(int userId) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        UserDTO user = null;
        try {
            con = DataSource.getConnection();
            pstmt = con.prepareStatement("SELECT user_id, first_name, last_name, phone, address FROM User WHERE user_id = ?");
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new UserDTO();
                user.setUserId(rs.getInt("user_id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
            }
        } finally {
            closeResources(rs, pstmt, con);
        }
        return user;
    }

    public void updateUser(UserDTO user) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DataSource.getConnection();
            pstmt = con.prepareStatement("UPDATE User SET first_name = ?, last_name = ?, phone = ?, address = ? WHERE user_id = ?");
            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getPhone());
            pstmt.setString(4, user.getAddress());
            pstmt.setInt(5, user.getUserId());
            pstmt.executeUpdate();
        } finally {
            closeResources(null, pstmt, con);
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