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
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.UserDTO;

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
        try {
        List<InventoryDTO> inventoryList = consumerBusinessLogic.getFilteredInventory();
        request.setAttribute("inventoryList", inventoryList);
        
       
//        to get user ID by static, will be change to session=================
        String userIdStr = "1";
        
        if (userIdStr != null && !userIdStr.isEmpty()) {
            int userId = Integer.parseInt(userIdStr);
            
         
            UserDTO user = consumerBusinessLogic.getUserCreditById(userId);
            
            request.setAttribute("credit", user.getCredit());
            request.setAttribute("user", user); // 添加用户对象到请求
        } else {
            request.setAttribute("credit", 5.0); // 如果用户 ID 为空，默认信用值为 0
        }
        
        request.getRequestDispatcher("/views/purchaseView.jsp").forward(request, response);
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
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
    int userId = Integer.parseInt(request.getParameter("userId"));
    String[] foodIds = request.getParameterValues("foodId");

    System.out.println("userId: " + userId);
    System.out.println("foodIds: " + (foodIds != null ? Arrays.toString(foodIds) : "null"));

    if (foodIds == null) {
        request.setAttribute("message", "No items selected for purchase.");
        doGet(request, response);
        return;
    }

    List<TransactionItemDTO> transactionItems = new ArrayList<>();
    UserTransactionDTO userTransaction = new UserTransactionDTO();
    userTransaction.setUserId(userId);
    userTransaction.setTransactionDate(new Date());
    
    double totalPrice = 0.0;

    try {
        consumerBusinessLogic.addUserTransaction(userTransaction);
        int userTransactionId = userTransaction.getUserTransactionId();
        
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
            totalPrice += price * quantity;

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

        UserDTO user = consumerBusinessLogic.getUserCreditById(userId);
        double credit = user.getCredit();
        double actualPayment = totalPrice - credit;
        double newCredit = actualPayment * 0.01;
        user.setCredit(newCredit);
        consumerBusinessLogic.updateUserCredit(user);

        // 新添加的日志
        System.out.println("User ID: " + userId + ", New Credit: " + user.getCredit());

        request.setAttribute("totalPrice", totalPrice);
        request.setAttribute("credit", credit);
        request.setAttribute("actualPayment", actualPayment);
        request.setAttribute("message", "Purchase successful!");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("message", "An error occurred.");
        }

        doGet(request, response);
    }
}