package dataAccessLayer;

import java.util.List;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.TransactionItemDTO;

/**
 * Data Access Object implementation for Transaction Items.
 * Provides methods to perform CRUD operations on the TransactionItem table.
 * 
 */
public class TransactionItemDaoImpl {

    public TransactionItemDaoImpl() {
    }

    /**
     * Retrieves all transaction items from the database.
     *
     * @return a list of TransactionItemDTO objects
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the JDBC driver class is not found
     */
    public List<TransactionItemDTO> getAllTransactionItems() throws SQLException, ClassNotFoundException {
        ArrayList<TransactionItemDTO> items = null;
        String query = "SELECT transactionitem_id, usertransaction_id, food_id, quantity, price FROM TransactionItem ORDER BY transactionitem_id";

        try (Connection con = DataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            items = new ArrayList<>();
            while (rs.next()) {
                TransactionItemDTO item = new TransactionItemDTO();
                item.setTransactionItemId(rs.getInt("transactionitem_id"));
                item.setUserTransactionId(rs.getInt("usertransaction_id"));
                item.setFoodId(rs.getInt("food_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPrice(rs.getDouble("price"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return items;
    }

    /**
     * Adds a new transaction item to the database.
     *
     * @param item the TransactionItemDTO object to be added
     * @throws ClassNotFoundException if the JDBC driver class is not found
     */
    public void addTransactionItem(TransactionItemDTO item) throws ClassNotFoundException {
        String query = "INSERT INTO TransactionItem (usertransaction_id, food_id, quantity, price) VALUES(?, ?, ?, ?)";

        try (Connection con = DataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setInt(1, item.getUserTransactionId());
            pstmt.setInt(2, item.getFoodId());
            pstmt.setInt(3, item.getQuantity());
            pstmt.setDouble(4, item.getPrice());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}