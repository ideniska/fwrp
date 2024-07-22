package businesslayer;

import java.sql.SQLException;
import model.UserDTO;
import dataAccessLayer.UserDaoImpl;

/**
 * Business logic layer for managing orders.
 * Provides methods to perform operations on user details.
 * 
 * author Yuchen Wang
 */
public class OrderBusinessLogic {
    private UserDaoImpl userDao = null;

    public OrderBusinessLogic() {
        userDao = new UserDaoImpl();
    }

    public UserDTO getUserById(int userId) throws SQLException, ClassNotFoundException {
        return userDao.getUserById(userId);
    }

    public void updateUser(UserDTO user) throws SQLException, ClassNotFoundException {
        userDao.updateUser(user);
    }
}