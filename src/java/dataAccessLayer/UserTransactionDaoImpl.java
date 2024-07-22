
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

    public void addUserTransaction(UserTransactionDTO transaction) throws ClassNotFoundException {
        String query = "INSERT INTO UserTransaction (user_id, transaction_date) VALUES(?, ?)";

        try (Connection con = DataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setInt(1, transaction.getUserId());
            pstmt.setDate(2, new java.sql.Date(transaction.getTransactionDate().getTime()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}