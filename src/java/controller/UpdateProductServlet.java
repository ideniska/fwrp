package controller;

import businesslayer.RetailerBusinessLogic;
import model.InventoryDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateProductServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int foodId = Integer.parseInt(request.getParameter("foodId"));
        String foodName = request.getParameter("foodName");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        Date expDate = null;
        try {
            expDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("expDate"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Integer price = Integer.parseInt(request.getParameter("price"));

        InventoryDTO product = new InventoryDTO();
        product.setFoodId(foodId);
        product.setFoodName(foodName);
        product.setQuantity(quantity);
        product.setExpDate(expDate);
        product.setPrice(price);

        RetailerBusinessLogic retailerBusinessLogic = new RetailerBusinessLogic();
        retailerBusinessLogic.updateInventory(product);

        response.sendRedirect("detail?foodId=" + foodId);
    }
}