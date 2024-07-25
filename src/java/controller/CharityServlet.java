/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import businesslayer.CharityBusinessLogic;
import businesslayer.CheckoutBusinessLogic;
import static com.mysql.cj.conf.PropertyKey.logger;
import model.InventoryDTO;
import javax.servlet.RequestDispatcher;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import model.TransactionItemDTO;
import model.UserTransactionDTO;

/**
 *
 * @author denissakhno
 */


public class CharityServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CharityBusinessLogic charityBusinessLogic;
    private static final Logger logger = Logger.getLogger(CharityServlet.class.getName());

    @Override
    public void init() {
        this.charityBusinessLogic = new CharityBusinessLogic();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<InventoryDTO> inventoryItems = charityBusinessLogic.getCharityInventory();
            request.setAttribute("inventoryItems", inventoryItems);
            RequestDispatcher dispatcher = request.getRequestDispatcher("views/charityView.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException("Error retrieving charity inventory items", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        String[] foodIds = request.getParameterValues("foodId");

        if (foodIds == null) {
            request.setAttribute("message", "No items selected for claiming.");
            doGet(request, response);
            return;
        }

        List<TransactionItemDTO> transactionItems = new ArrayList<>();
        UserTransactionDTO userTransaction = new UserTransactionDTO();
        userTransaction.setUserId(userId);
        userTransaction.setTransactionDate(new Date());

        try {
            charityBusinessLogic.addUserTransaction(userTransaction);
            int userTransactionId = userTransaction.getUserTransactionId();

            for (String foodIdStr : foodIds) {
                int foodId = Integer.parseInt(foodIdStr);
                String quantityStr = request.getParameter("claimQuantity_" + foodId);
                String priceStr = request.getParameter("price_" + foodId);

                if (quantityStr == null || quantityStr.isEmpty() || priceStr == null || priceStr.isEmpty()) {
                    request.setAttribute("message", "Quantity or price is missing for foodId: " + foodId);
                    doGet(request, response);
                    return;
                }

                int quantity = Integer.parseInt(quantityStr);
                double price = Double.parseDouble(priceStr);

                InventoryDTO item = charityBusinessLogic.getInventoryById(foodId);
                item.setQuantity(item.getQuantity() - quantity);
                charityBusinessLogic.updateInventory(item);

                TransactionItemDTO transactionItem = new TransactionItemDTO();
                transactionItem.setUserTransactionId(userTransactionId);
                transactionItem.setFoodId(foodId);
                transactionItem.setQuantity(quantity);
                transactionItem.setPrice(price);
                charityBusinessLogic.addTransactionItem(transactionItem);
            }

            request.setAttribute("message", "Claim successful!");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("message", "An error occurred.");
        }

        // Forward back to the same page with updated data
        doGet(request, response);
    }
}