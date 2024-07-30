package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.InventoryDTO;
import businesslayer.RetailerBusinessLogic;
import javax.servlet.annotation.WebServlet;
import model.UserDTO;
import model.UserType;
import util.AuthUtils;

public class RetailerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        if (!AuthUtils.checkUserType(request, UserType.RETAILER)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
        int userId = user.getUserId();  // Get userId from the UserDTO object

        RetailerBusinessLogic retailerBusinessLogic = new RetailerBusinessLogic();
        List<InventoryDTO> inventory = null;

        try {
            inventory = retailerBusinessLogic.getInventoryByUserId(userId);
        } catch (SQLException | ClassNotFoundException ex) {
            log(ex.getMessage());
        }

        request.setAttribute("inventoryList", inventory);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/inventoryManagement.jsp");
        dispatcher.forward(request, response); 
    }
}
