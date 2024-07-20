package controller;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.InventoryDTO;
import businesslayer.RetailerBusinessLogic;
import javax.servlet.annotation.WebServlet;
/**
 *
 * @author denissakhno
 */

public class RetailerServlet extends HttpServlet{
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        RetailerBusinessLogic retailerBusinessLogic = new RetailerBusinessLogic();
        List<InventoryDTO> inventory = null;

        try {
            inventory = retailerBusinessLogic.getAllInventory();
        } catch (SQLException | ClassNotFoundException ex) {
            log(ex.getMessage());
        }

        request.setAttribute("inventoryList", inventory);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/inventoryManagement.jsp");
        dispatcher.forward(request, response); 
    }
}
