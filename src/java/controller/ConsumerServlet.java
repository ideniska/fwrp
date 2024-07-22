package controller;

import businesslayer.ConsumerBusinessLogic;
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

@WebServlet("/consumer")
public class ConsumerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ConsumerBusinessLogic consumerBusinessLogic;

    public ConsumerServlet() {
        this.consumerBusinessLogic = new ConsumerBusinessLogic();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<InventoryDTO> inventoryList = consumerBusinessLogic.getFilteredInventory();
            request.setAttribute("inventoryList", inventoryList);
            request.getRequestDispatcher("/views/purchaseView.jsp").forward(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        String[] foodIds = request.getParameterValues("foodId");

        if (foodIds == null) {
            request.setAttribute("message", "No items selected for purchase.");
            doGet(request, response);
            return;
        }

        boolean allSuccess = true;

        try {
            for (String foodIdStr : foodIds) {
                int foodId = Integer.parseInt(foodIdStr);
                String quantityStr = request.getParameter("quantity_" + foodId);
                String priceStr = request.getParameter("price_" + foodId);

                // 检查quantity和price是否为空
                if (quantityStr == null || quantityStr.isEmpty()) {
                    request.setAttribute("message", "Quantity is missing for foodId: " + foodId);
                    doGet(request, response);
                    return;
                }

                if (priceStr == null || priceStr.isEmpty()) {
                    request.setAttribute("message", "Price is missing for foodId: " + foodId);
                    doGet(request, response);
                    return;
                }

                int quantity = Integer.parseInt(quantityStr);
                double price = Double.parseDouble(priceStr);

                boolean success = consumerBusinessLogic.updateInventory(foodId, quantity);
                if (success) {
                    consumerBusinessLogic.logPurchase(userId, foodId, quantity, price);
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
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("message", "An error occurred.");
            doGet(request, response);
        }
    }
}