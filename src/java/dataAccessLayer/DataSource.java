package dataAccessLayer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * This class provides a method to establish a connection to the database using
 * JDBC.
 */
public class DataSource {

    /**
     * Private constructor to prevent instantiation.
     */
    private DataSource() {
    }

//    private static Connection connection = null;

    /**
     * Returns a Connection object to the database. If the connection does not
     * already exist, it creates a new one using the properties file.
     *
     * @return a Connection object to the database
     */
    public static Connection getConnection() throws ClassNotFoundException {
        String[] connectionInfo = openPropsFile();
        Connection connection = null;

        try {
            if (connection == null) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(connectionInfo[0], connectionInfo[1], connectionInfo[2]);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return connection;
    }

    /**
     * Reads the database connection properties from a properties file.
     *
     * @return an array of Strings containing the connection URL, username, and
     *         password
     */
    public static String[] openPropsFile() {
        Properties props = new Properties();

        try (InputStream in = DataSource.class.getClassLoader().getResourceAsStream("resources/db.properties")) {
            if (in == null) {
                throw new IOException("Unable to find db.properties");
            }
            props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String connectionString = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        return new String[]{connectionString, username, password};
    }
}