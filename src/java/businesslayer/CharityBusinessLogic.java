/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package businesslayer;

import dataAccessLayer.CharityInventoryDaoImpl;
import model.InventoryDTO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author dmlop
 */

public class CharityBusinessLogic {
    private CharityInventoryDaoImpl charityInventoryDao;

    public CharityBusinessLogic() {
        this.charityInventoryDao = new CharityInventoryDaoImpl();
    }

    public List<InventoryDTO> getCharityInventory() throws SQLException, ClassNotFoundException {
        return charityInventoryDao.getCharityInventory();
    }

    public void claimItem(int foodId, int claimQuantity) throws SQLException, ClassNotFoundException {
        charityInventoryDao.updateInventory(foodId, claimQuantity);
    }
}