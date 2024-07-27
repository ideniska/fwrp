package controller;

import dataAccessLayer.InventoryDaoImpl;
import model.InventoryDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.TransactionItemDTO;
import model.UserTransactionDTO;
import businesslayer.ConsumerBusinessLogic;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.UserType;
import util.AuthUtils;

/**
 * Servlet implementation class ConsumerServlet.
 * Handles consumer actions including displaying available inventory
 * and processing purchase transactions.
 * 
 * author: Yuchen Wang
 */

public class ConsumerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ConsumerBusinessLogic consumerBusinessLogic;
    
    /**
     * Default constructor. Initializes the ConsumerBusinessLogic instance.
     */
    public ConsumerServlet(){
        consumerBusinessLogic = new ConsumerBusinessLogic();
    }
    
    /**
     * Handles the HTTP GET method.
     * Retrieves the filtered inventory and forwards to the purchase view page.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!AuthUtils.checkUserType(request, UserType.CUSTOMER)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        try {
            InventoryDaoImpl dao = new InventoryDaoImpl();
            List<InventoryDTO> inventoryList = dao.getFilteredInventory();
            request.setAttribute("inventoryList", inventoryList);
            request.getRequestDispatcher("/views/purchaseView.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConsumerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     /**
     * Handles the HTTP POST method.
     * Processes the purchase transaction, updates inventory, and logs the transaction.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!AuthUtils.checkUserType(request, UserType.CUSTOMER)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        int userId = Integer.parseInt(request.getParameter("userId"));
        String[] foodIds = request.getParameterValues("foodId");

        if (foodIds == null) {
            request.setAttribute("message", "No items selected for purchase.");
            doGet(request, response);
            return;
        }

        List<TransactionItemDTO> transactionItems = new ArrayList<>();
        UserTransactionDTO userTransaction = new UserTransactionDTO();
        userTransaction.setUserId(userId);
        userTransaction.setTransactionDate(new Date());

       try {
           
            consumerBusinessLogic.addUserTransaction(userTransaction);
            int userTransactionId = userTransaction.getUserTransactionId();
            System.out.println(userTransactionId);

            for (String foodIdStr : foodIds) {
                int foodId = Integer.parseInt(foodIdStr);
                String quantityStr = request.getParameter("quantity_" + foodId);
                String priceStr = request.getParameter("price_" + foodId);
               

                if (quantityStr == null || quantityStr.isEmpty() || priceStr == null || priceStr.isEmpty()) {
                    request.setAttribute("message", "Quantity or price is missing for foodId: " + foodId);
                    doGet(request, response);
                    return;
                }

                int quantity = Integer.parseInt(quantityStr);
                double price = Double.parseDouble(priceStr);

                InventoryDTO item = consumerBusinessLogic.getInventoryById(foodId);
                item.setQuantity(item.getQuantity() - quantity);


                consumerBusinessLogic.updateInventory(item);

                TransactionItemDTO transactionItem = new TransactionItemDTO();
                transactionItem.setUserTransactionId(userTransactionId);
                transactionItem.setFoodId(foodId);
                transactionItem.setQuantity(quantity);
                transactionItem.setPrice(price);
                consumerBusinessLogic.addTransactionItem(transactionItem);
            }

            request.setAttribute("message", "Purchase successful!");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("message", "An error occurred.");
        }

        doGet(request, response);
    }
}