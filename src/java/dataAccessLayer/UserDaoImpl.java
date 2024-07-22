package dataAccessLayer;

import java.util.List;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.UserDTO;


public class UserDaoImpl {

    public UserDaoImpl() {
    }

    public List<UserDTO> getAllUsers() throws SQLException, ClassNotFoundException {
        List<UserDTO> users = new ArrayList<>();
        String query = "SELECT user_id, first_name, last_name, phone, address, email, password, user_type, location, communication, food_preference, notifications FROM User ORDER BY user_id";

        try (Connection con = DataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

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
        }
        return users;
    }

    public void addUser(UserDTO user) throws ClassNotFoundException {
        String query = "INSERT INTO User (first_name, last_name, phone, address, email, password, user_type, location, communication, food_preference, notifications) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection con = DataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

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
        }
    }

    public UserDTO authenticateUser(String email, String password) {
        UserDTO user = null;
        String query = "SELECT * FROM User WHERE email = ? AND password = ?";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    
    public UserDTO getUserById(int userId) throws SQLException, ClassNotFoundException {
        UserDTO user = null;
        String query = "SELECT user_id, first_name, last_name, phone, address FROM User WHERE user_id = ?";

        try (Connection con = DataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = new UserDTO();
                    user.setUserId(rs.getInt("user_id"));
                    user.setFirstName(rs.getString("first_name"));
                    user.setLastName(rs.getString("last_name"));
                    user.setPhone(rs.getString("phone"));
                    user.setAddress(rs.getString("address"));
                }
            }
        }
        return user;
    }
    
    public void updateUser(UserDTO user) throws SQLException, ClassNotFoundException {
        String query = "UPDATE User SET first_name = ?, last_name = ?, phone = ?, address = ? WHERE user_id = ?";

        try (Connection con = DataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getPhone());
            pstmt.setString(4, user.getAddress());
            pstmt.setInt(5, user.getUserId());
            pstmt.executeUpdate();
        }
    }
}