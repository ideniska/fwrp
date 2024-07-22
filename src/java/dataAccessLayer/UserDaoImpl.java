package dataAccessLayer;

import model.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl {

    public UserDaoImpl() {
    }

    public UserDTO getUserById(int userId) throws SQLException, ClassNotFoundException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        UserDTO user = null;
        try {
            con = DataSource.getConnection();
            pstmt = con.prepareStatement(
                    "SELECT user_id, first_name, last_name, phone, address FROM User WHERE user_id = ?");
            pstmt.setInt(1, userId);
            pstmt = con.prepareStatement(
                    "SELECT user_id, first_name, last_name, phone, address, email, password, user_type, location, communication, food_preference, notifications FROM User ORDER BY user_id");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new UserDTO();
                user.setUserId(rs.getInt("user_id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setUserType(rs.getInt("user_type"));
                user.setLocation(rs.getString("location"));
                user.setCommunication(rs.getInt("communication"));
                user.setFoodPreference(rs.getString("food_preference"));
                user.setNotifications(rs.getInt("notifications"));
                users.add(user);
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
        return users;
    }

    public void addUser(UserDTO user) throws ClassNotFoundException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DataSource.getConnection();
            pstmt = con.prepareStatement(
                    "UPDATE User SET first_name = ?, last_name = ?, phone = ?, address = ? WHERE user_id = ?");
            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getPhone());
            pstmt.setString(4, user.getAddress());
            pstmt.setInt(5, user.getUserId());
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

    public UserDTO authenticateUser(String email, String password) {
        UserDTO user = null;
        try (Connection connection = DataSource.getConnection();
                PreparedStatement ps = connection
                        .prepareStatement("SELECT * FROM User WHERE email = ? AND password = ?")) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new UserDTO();
                user.setUserId(rs.getInt("user_id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setAddress(rs.getString("address"));
                user.setPhone(rs.getString("phone"));
                user.setEmail(rs.getString("email"));
                user.setUserType(rs.getInt("user_type"));
                user.setLocation(rs.getString("location"));
                user.setCommunication(rs.getInt("communication"));
                user.setFoodPreference(rs.getString("food_preference"));
                user.setNotifications(rs.getInt("notifications"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
