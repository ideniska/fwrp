
package dataAccessLayer;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author denissakhno
 */
public class DataSourceTest {
    
    public DataSourceTest() {
    }
    
    @After
    public void tearDown() throws SQLException, ClassNotFoundException {
        Connection connection = DataSource.getConnection();
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    public void testGetConnection() throws ClassNotFoundException, SQLException {
        Connection connection = DataSource.getConnection();
        assertNotNull("Connection should not be null", connection);
        assertTrue("Connection should be valid", connection.isValid(2));
    }
    
}
