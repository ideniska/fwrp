package controller;

import businesslayer.RetailerBusinessLogic;
import model.InventoryDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateProductServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        RetailerBusinessLogic retailerBusinessLogic = new RetailerBusinessLogic();

        if ("update".equals(action)) {
            int foodId = Integer.parseInt(request.getParameter("foodId"));
            String foodName = request.getParameter("foodName");
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            Date expDate = null;
            try {
                expDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("expDate"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Double price = Double.parseDouble(request.getParameter("price"));
            int surplus = Integer.parseInt(request.getParameter("surplus"));

            InventoryDTO product = new InventoryDTO();
            product.setFoodId(foodId);
            product.setFoodName(foodName);
            product.setQuantity(quantity);
            product.setExpDate(expDate);
            product.setPrice(price);
            product.setSurplus(surplus); // Add this line

            try {
                retailerBusinessLogic.updateInventory(product);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            response.sendRedirect("productDetail?foodId=" + foodId);
        } else if ("delete".equals(action)) {
            int foodId = Integer.parseInt(request.getParameter("foodId"));
            try {
                retailerBusinessLogic.deleteInventory(foodId);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            response.sendRedirect("/FWRP/retailer");
        }
    }
}