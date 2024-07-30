
package test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import dataAccessLayer.DataSource;
import java.sql.Connection;

import org.junit.Before;
import org.junit.Test;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;




public class MainTest {
    // test Charity Inventory DAO

    
    
    // test Data Source
    @Test
    public void dbConnection() throws ClassNotFoundException {
        Connection con = DataSource.getConnection();
    }
    
    
    // test Inventory Dao
    // test Transaction Item DAO
    // test User Dao
    // test User Transaction Dao
    
    // test Charity Business Logic
    // test Consumer Business Logic
    // test Order Business Logic
    // how can I test servlets?
    
    // test notifications
    // test cashback
    
}
