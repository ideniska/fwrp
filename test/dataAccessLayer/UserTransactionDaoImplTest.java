package dataAccessLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.List;
import model.UserTransactionDTO;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.Date;

public class UserTransactionDaoImplTest {

    @Test
    public void testGetAllUserTransactions() throws Exception {
        UserTransactionDaoImpl instance = new UserTransactionDaoImpl();
        List<UserTransactionDTO> result = instance.getAllUserTransactions();
        assertNotNull("User transactions list should not be null", result);
        assertTrue("User transactions list should not be empty", result.size() > 0);
    }

    @Test
    public void testAddUserTransaction() throws Exception {
        UserTransactionDaoImpl instance = new UserTransactionDaoImpl();
        UserTransactionDTO transaction = new UserTransactionDTO();
        transaction.setUserId(1); 
        transaction.setTransactionDate(Date.valueOf("2023-07-01"));

        instance.addUserTransaction(transaction);

        assertTrue("Transaction ID should be greater than 0 after insert", transaction.getUserTransactionId() > 0);

        try (Connection connection = DataSource.getConnection();
             Statement stmt = connection.createStatement()) {
            String query = "SELECT * FROM UserTransaction WHERE usertransaction_id = " + transaction.getUserTransactionId();
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            assertTrue(rs.next());
            assertEquals(1, rs.getInt("user_id"));
            assertEquals(Date.valueOf("2023-07-01"), rs.getDate("transaction_date"));
        }
    }

}