package dataAccessLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.UserDTO;

/**
 * Data Access Object implementation for User.
 * Provides methods to perform CRUD operations on the User table.
 * 

 */
public class UserDaoImpl {

    public UserDaoImpl() {
    }

    /**
     * Retrieves all users from the database.
     *
     * @return a list of UserDTO objects
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the JDBC driver class is not found
     */
    public List<UserDTO> getAllUsers() throws SQLException, ClassNotFoundException {
        List<UserDTO> users = new ArrayList<>();
        String query = "SELECT user_id, first_name, last_name, phone, address, email, password, user_type, location, food_preference, notifications, org_name FROM User ORDER BY user_id";

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

    /**
     * Adds a new user to the database.
     *
     * @param user the UserDTO object to be added
     * @return true if the user was successfully added, false if the email already exists
     * @throws ClassNotFoundException if the JDBC driver class is not found
     */
    public boolean addUser(UserDTO user) throws ClassNotFoundException {
        String checkQuery = "SELECT email FROM User WHERE email = ?";
        String insertQuery = "INSERT INTO User (first_name, last_name, email, password, user_type, address, phone, location, food_preference, notifications, org_name) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DataSource.getConnection();
                PreparedStatement checkPstmt = con.prepareStatement(checkQuery)) {

            checkPstmt.setString(1, user.getEmail());
            try (ResultSet rs = checkPstmt.executeQuery()) {
                if (rs.next()) {
                    return false; // Email already exists
                }
            }

            try (PreparedStatement insertPstmt = con.prepareStatement(insertQuery)) {
                // Set required fields
                insertPstmt.setString(1, user.getFirstName());
                insertPstmt.setString(2, user.getLastName());
                insertPstmt.setString(3, user.getEmail());
                insertPstmt.setString(4, user.getPassword());
                insertPstmt.setInt(5, user.getUserType());

                // Set optional fields, default to empty string if not provided
                insertPstmt.setString(6, user.getAddress() != null ? user.getAddress() : "");
                insertPstmt.setString(7, user.getPhone() != null ? user.getPhone() : "");
                insertPstmt.setString(8, user.getLocation() != null ? user.getLocation() : "Ottawa");
                insertPstmt.setString(9, user.getFoodPreference() != null ? user.getFoodPreference() : "");
                insertPstmt.setInt(10, user.getNotifications() != null ? user.getNotifications() : 1); // Default to 'No'
                insertPstmt.setString(11, user.getOrgName() != null ? user.getOrgName() : "");

                insertPstmt.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Authenticates a user by email and password.
     *
     * @param email the email of the user
     * @param password the password of the user
     * @return the authenticated UserDTO object, or null if authentication fails
     */
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

    /**
     * Retrieves a user by their ID.
     *
     * @param userId the ID of the user to be retrieved
     * @return the UserDTO object representing the user
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the JDBC driver class is not found
     */
    public UserDTO getUserById(int userId) throws SQLException, ClassNotFoundException {
        UserDTO user = null;
        String query = "SELECT * FROM User WHERE user_id = ?";

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
                    user.setPhone(rs.getString("phone"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setUserType(rs.getInt("user_type"));
                    user.setLocation(rs.getString("location"));
                    user.setFoodPreference(rs.getString("food_preference"));
                    user.setNotifications(rs.getInt("notifications"));
                    user.setCredit(rs.getDouble("credit"));
                    user.setOrgName(rs.getString("org_name"));
                }
            }
        }
        return user;
    }

    /**
     * Updates an existing user in the database.
     *
     * @param user the UserDTO object containing updated information
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the JDBC driver class is not found
     */
    public void updateUser(UserDTO user) throws SQLException, ClassNotFoundException {
        String query = "UPDATE User SET phone = ?, address = ?, org_name = ?, location = ?, food_preference = ?, notifications = ? WHERE user_id = ?";

        try (Connection con = DataSource.getConnection();
                PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, user.getPhone());
            pstmt.setString(2, user.getAddress());
            pstmt.setString(3, user.getOrgName());
            pstmt.setString(4, user.getLocation());
            pstmt.setString(5, user.getFoodPreference());
            pstmt.setInt(6, user.getNotifications());
            pstmt.setInt(7, user.getUserId());

            pstmt.executeUpdate();
        }
    }
    
    

    /**
     * Retrieves the credit of a user by their ID.
     *
     * @param userId the ID of the user
     * @return the UserDTO object containing the user's credit
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the JDBC driver class is not found
     */
    public UserDTO getUserCreditById(int userId) throws SQLException, ClassNotFoundException {
        UserDTO user = null;
        String query = "SELECT credit FROM User WHERE user_id = ?";

        try (Connection con = DataSource.getConnection();
                PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = new UserDTO();
                    user.setUserId(userId);
                    user.setCredit(rs.getDouble("credit"));
                }
            }
        }
        return user;
    }

    public UserDTO getUserByEmail(String email) {
    UserDTO user = null;
    String sql = "SELECT * FROM User WHERE email = ?";
    
    try (Connection conn = DataSource.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setString(1, email);
        
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                user = new UserDTO();
                user.setUserId(rs.getInt("user_id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setUserType(rs.getInt("user_type"));
                user.setAddress(rs.getString("address"));
                user.setPhone(rs.getString("phone"));
                user.setLocation(rs.getString("location"));
                user.setFoodPreference(rs.getString("food_preference"));
                user.setNotifications(rs.getInt("notifications"));
                user.setOrgName(rs.getString("org_name"));
                user.setCredit(rs.getDouble("credit"));
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    
    return user;
}
    
    /**
     * Updates the credit of an existing user in the database.
     *
     * @param user the UserDTO object containing updated credit information
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the JDBC driver class is not found
     */
public void updateUserCredit(UserDTO user) throws SQLException, ClassNotFoundException {
    String query = "UPDATE User SET credit = ? WHERE user_id = ?";

    try (Connection con = DataSource.getConnection();
            PreparedStatement pstmt = con.prepareStatement(query)) {

        pstmt.setDouble(1, user.getCredit());
        pstmt.setInt(2, user.getUserId());
        pstmt.executeUpdate();
    }
}



}