package dataAccessLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.UserDTO;

public class UserDaoImpl {

    public UserDaoImpl() {
    }

    public List<UserDTO> getAllUsers() throws SQLException, ClassNotFoundException {
        List<UserDTO> users = new ArrayList<>();
        String query = "SELECT user_id, first_name, last_name, phone, address, email, password, user_type, location, communication, food_preference, notifications, org_name FROM User ORDER BY user_id";

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
                user.setOrgName(rs.getString("org_name"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return users;
    }

    public boolean addUser(UserDTO user) throws ClassNotFoundException {
        String checkQuery = "SELECT email FROM User WHERE email = ?";
        String insertQuery = "INSERT INTO User (first_name, last_name, phone, address, email, password, user_type, location, communication, food_preference, notifications, org_name) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DataSource.getConnection();
             PreparedStatement checkPstmt = con.prepareStatement(checkQuery)) {

            checkPstmt.setString(1, user.getEmail());
            try (ResultSet rs = checkPstmt.executeQuery()) {
                if (rs.next()) {
                    return false; // Email already exists
                }
            }

            try (PreparedStatement insertPstmt = con.prepareStatement(insertQuery)) {
                insertPstmt.setString(1, user.getFirstName());
                insertPstmt.setString(2, user.getLastName());
                insertPstmt.setString(3, user.getPhone());
                insertPstmt.setString(4, user.getAddress());
                insertPstmt.setString(5, user.getEmail());
                insertPstmt.setString(6, user.getPassword());
                insertPstmt.setInt(7, user.getUserType());
                insertPstmt.setString(8, user.getLocation());
                insertPstmt.setInt(9, user.getCommunication());
                insertPstmt.setString(10, user.getFoodPreference());
                insertPstmt.setInt(11, user.getNotifications());
                insertPstmt.setString(12, user.getOrgName());
                insertPstmt.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
                    user.setOrgName(rs.getString("org_name"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public UserDTO getUserById(int userId) throws SQLException, ClassNotFoundException {
        UserDTO user = null;
        String query = "SELECT user_id, first_name, last_name, phone, address, org_name FROM User WHERE user_id = ?";

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
                    user.setOrgName(rs.getString("org_name"));
                }
            }
        }
        return user;
    }

    public void updateUser(UserDTO user) throws SQLException, ClassNotFoundException {
        String query = "UPDATE User SET first_name = ?, last_name = ?, phone = ?, address = ?, org_name = ? WHERE user_id = ?";

        try (Connection con = DataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getPhone());
            pstmt.setString(4, user.getAddress());
            pstmt.setString(5, user.getOrgName());
            pstmt.setInt(6, user.getUserId());
            pstmt.executeUpdate();
        }
    }
}
