/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yuchen Wang
 */

@WebServlet("/consumer")
public class ConsumerServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws SecurityException,IOException, ServletException{
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
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        String[] foodIds = request.getParameterValues("foodId");

        InventoryDaoImpl dao = new InventoryDaoImpl();
        boolean allSuccess = true;

        try {
            for (String foodIdStr : foodIds) {
                int foodId = Integer.parseInt(foodIdStr);
                int quantity = Integer.parseInt(request.getParameter("quantity_" + foodId));
                double price = Double.parseDouble(request.getParameter("price_" + foodId));

                boolean success = dao.updateInventory(foodId, quantity);
                if (success) {
                    dao.logPurchase(userId, foodId, quantity, price);
                } else {
                    allSuccess = false;
                    break;
                }
            }

            if (allSuccess) {
                response.sendRedirect("order?userId=" + userId);
            } else {
                request.setAttribute("message", "Purchase failed. Not enough stock.");
                doGet(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "An error occurred.");
            doGet(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConsumerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
