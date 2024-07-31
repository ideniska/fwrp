package dataAccessLayer;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import model.UserTransactionDTO;

/**
 * Data Access Object implementation for User Transactions.
 * Provides methods to perform CRUD operations on the UserTransaction table.
 * 
 */
public class UserTransactionDaoImpl {

    public UserTransactionDaoImpl() {
    }

    /**
     * Retrieves all user transactions from the database.
     *
     * @return a list of UserTransactionDTO objects
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the JDBC driver class is not found
     */
    public List<UserTransactionDTO> getAllUserTransactions() throws SQLException, ClassNotFoundException {
        List<UserTransactionDTO> transactions = new ArrayList<>();
        String query = "SELECT usertransaction_id, user_id, transaction_date FROM UserTransaction ORDER BY usertransaction_id";

        try (Connection con = DataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

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
        }
        return transactions;
    }

    /**
     * Adds a new user transaction to the database.
     *
     * @param transaction the UserTransactionDTO object to be added
     * @throws ClassNotFoundException if the JDBC driver class is not found
     */
    public void addUserTransaction(UserTransactionDTO transaction) throws ClassNotFoundException {
        String query = "INSERT INTO UserTransaction (user_id, transaction_date) VALUES(?, ?)";

        try (Connection con = DataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, transaction.getUserId());
            pstmt.setDate(2, new java.sql.Date(transaction.getTransactionDate().getTime()));
            pstmt.executeUpdate();
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    transaction.setUserTransactionId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}