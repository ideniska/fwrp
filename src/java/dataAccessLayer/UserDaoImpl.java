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
import model.UserDTO;

/**
 *
 * @author denissakhno
 */
public class UserDaoImpl {

    public UserDaoImpl() {
    }

    public List<UserDTO> getAllUsers() throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<UserDTO> users = null;
        try {
            con = DataSource.getConnection();
            pstmt = con.prepareStatement(
                    "SELECT user_id, first_name, last_name, phone, address, email, password, user_type, location, communication, food_preference, notifications FROM User ORDER BY user_id");
            rs = pstmt.executeQuery();
            users = new ArrayList<UserDTO>();
            while (rs.next()) {
                UserDTO user = new UserDTO();
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
                    "INSERT INTO User (first_name, last_name, phone, address, email, password, user_type, location, communication, food_preference, notifications) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getPhone());
            pstmt.setString(4, user.getAddress());
            pstmt.setString(5, user.getEmail());
            pstmt.setString(6, user.getPassword());
            pstmt.setInt(7, user.getUserType());
            pstmt.setString(8, user.getLocation());
            pstmt.setInt(9, user.getCommunication());
            pstmt.setString(10, user.getFoodPreference());
            pstmt.setInt(11, user.getNotifications());
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
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM User WHERE email = ? AND password = ?")) {
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
