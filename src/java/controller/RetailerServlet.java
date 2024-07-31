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
import model.UserDTO;
import model.UserType;
import util.AuthUtils;

/**
 * RetailerServlet handles the retrieval and display of the retailer's inventory.
 * 
 * author Denis Sakhno
 */
public class RetailerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles the HTTP GET method.
     * Checks the user type, retrieves the inventory for the logged-in retailer, and forwards the request to the inventory management view.
     *
     * @param request  the HttpServletRequest object that contains the request the client made to the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if the request could not be handled
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        if (!AuthUtils.checkUserType(request, UserType.RETAILER)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
        int userId = user.getUserId();  

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