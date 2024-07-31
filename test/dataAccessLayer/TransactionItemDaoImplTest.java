package dataAccessLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.List;
import model.TransactionItemDTO;
import org.junit.Test;
import static org.junit.Assert.*;

public class TransactionItemDaoImplTest {

    @Test
    public void testGetAllTransactionItems() throws Exception {
        TransactionItemDaoImpl instance = new TransactionItemDaoImpl();
        List<TransactionItemDTO> result = instance.getAllTransactionItems();
        assertNotNull("Transaction items list should not be null", result);
        assertTrue("Transaction items list should not be empty", result.size() > 0);
    }

    @Test
    public void testAddTransactionItem() throws Exception {
        TransactionItemDaoImpl instance = new TransactionItemDaoImpl();
        TransactionItemDTO item = new TransactionItemDTO();
        item.setUserTransactionId(1);
        item.setFoodId(3);
        item.setQuantity(12);
        item.setPrice(9.99);

        instance.addTransactionItem(item);

        try (Connection connection = DataSource.getConnection();
                ) {
            String query = "SELECT * FROM TransactionItem WHERE usertransaction_id = 1 AND food_id = 3";
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            assertTrue(rs.next());
            assertEquals(12, rs.getInt("quantity"));
            assertEquals(9.99, rs.getDouble("price"), 0.01);
        }
    }

}