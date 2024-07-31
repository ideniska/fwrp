package dataAccessLayer;

import java.util.List;
import model.UserDTO;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.Connection;
import dataAccessLayer.UserDaoImpl;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImplTest {

    public UserDaoImplTest() {
    }


    /**
     * Test of getAllUsers method, of class UserDaoImpl.
     */
    @Test
    public void testGetAllUsers() throws Exception {
        UserDaoImpl instance = new UserDaoImpl();
        List<UserDTO> result = instance.getAllUsers();
        assertFalse(result.isEmpty());
    }

    /**
     * Test of addUser method, of class UserDaoImpl.
     */
    @Test
    public void testAddUser() throws Exception {
        
        Random random = new Random();
        
        UserDTO user = new UserDTO();
        user.setFirstName("Test" + random.nextInt(1000));
        user.setLastName("User" + random.nextInt(1000));
        user.setEmail("testuser" + random.nextInt(1000) + "@example.com");
        user.setPassword("password" + random.nextInt(1000));
        user.setUserType(1);
        user.setAddress("Test Address" + random.nextInt(1000));
        user.setPhone(String.valueOf(random.nextInt(900000000) + 1000000000L));
        user.setLocation("Test Location" + random.nextInt(1000));
        user.setOrgName("Test Org" + random.nextInt(1000));

        UserDaoImpl instance = new UserDaoImpl();
        boolean result = instance.addUser(user);
        assertTrue(result);

    }

    /**
     * Test of authenticateUser method, of class UserDaoImpl.
     */
    @Test
    public void testAuthenticateUser() throws Exception {
        String email = "testuser@example.com";
        String password = "password";

        UserDTO user = new UserDTO();
        user.setFirstName("Test");
        user.setLastName("User");
        user.setEmail(email);
        user.setPassword(password);
        user.setUserType(1);

        UserDaoImpl instance = new UserDaoImpl();
        instance.addUser(user);

        UserDTO result = instance.authenticateUser(email, password);
        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }

    /**
     * Test of getUserById method, of class UserDaoImpl.
     */
    @Test
    public void testGetUserById() throws Exception {
        Random random = new Random();
        UserDTO user = new UserDTO();
        String uniqueSuffix = String.valueOf(random.nextInt(1000));
        user.setFirstName("Test" + uniqueSuffix);
        user.setLastName("User" + uniqueSuffix);
        user.setEmail("testuser" + uniqueSuffix + "@example.com");
        user.setPassword("password" + uniqueSuffix);
        user.setUserType(1);
        user.setAddress("Test Address" + uniqueSuffix);
        user.setPhone(String.valueOf(random.nextInt(900000000) + 1000000000L));
        user.setLocation("Test Location" + uniqueSuffix);
        user.setOrgName("Test Org" + uniqueSuffix);

        UserDaoImpl instance = new UserDaoImpl();
        boolean userAdded = instance.addUser(user);
        assertTrue(userAdded);

        UserDTO addedUser = instance.getUserByEmail(user.getEmail());
        System.out.println(addedUser);
        int userId = addedUser.getUserId();

        UserDTO result = instance.getUserById(userId);
        assertEquals(user.getEmail(), result.getEmail());
    }

    /**
     * Test of updateUser method, of class UserDaoImpl.
     */
@Test
    public void testUpdateUser() throws Exception {
        UserDTO user = new UserDTO();
        UserDaoImpl instance = new UserDaoImpl();
        user = instance.getUserById(10);
        user.setPhone("9876543210");

        instance.updateUser(user);
        UserDTO updatedUser = instance.getUserById(10);
        assertEquals("9876543210", updatedUser.getPhone());
    }

    /**
     * Test of getUserCreditById method, of class UserDaoImpl.
     */
    @Test
    public void testGetUserCreditById() throws Exception {
        int userId = 10;
        
        String query = "UPDATE User SET credit = ? WHERE user_id = ?";

        try (Connection con = DataSource.getConnection();
                PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setDouble(1, 100);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
        }
        


        UserDaoImpl instance = new UserDaoImpl();
        
        UserDTO result = instance.getUserCreditById(userId);
        assertNotNull(result);
        assertEquals(100.0, result.getCredit(), 0.0);

    }

    /**
     * Test of updateUserCredit method, of class UserDaoImpl.
     */
    @Test
    public void testUpdateUserCredit() throws Exception {
        
        UserDTO user = new UserDTO();
        UserDaoImpl instance = new UserDaoImpl();
        user = instance.getUserById(10);
        user.setCredit(150.0);
        instance.updateUserCredit(user);

        UserDTO updatedUser = instance.getUserCreditById(10);
        assertEquals(150.0, updatedUser.getCredit(), 0.0);

    }
}