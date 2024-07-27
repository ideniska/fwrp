package controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import model.InventoryDTO;
import businesslayer.RetailerBusinessLogic;
import model.UserType;
import util.AuthUtils;


public class AddProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthUtils.checkUserType(request, UserType.RETAILER)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        RetailerBusinessLogic retailerBusinessLogic = new RetailerBusinessLogic();
        int latestFoodId = 0;
        try {
            latestFoodId = retailerBusinessLogic.getLatestFoodId();
        } catch (ClassNotFoundException | SQLException ex) {
            log(ex.getMessage());
        }
        request.setAttribute("latestFoodId", latestFoodId + 1);
        request.getRequestDispatcher("/views/addProduct.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthUtils.checkUserType(request, UserType.RETAILER)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        RetailerBusinessLogic retailerBusinessLogic = new RetailerBusinessLogic();

        int foodId = Integer.parseInt(request.getParameter("foodId"));
        String foodName = request.getParameter("foodName");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String expDateString = request.getParameter("expDate");
        double price = Double.parseDouble(request.getParameter("price"));
        int surplus = Integer.parseInt(request.getParameter("surplus"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date expDate = null;

        try {
            expDate = formatter.parse(expDateString);
        } catch (ParseException e) {
            log("Date parsing error: " + e.getMessage());
        }

        if (price <= 0 || quantity <= 0 || expDate == null || expDate.before(new Date())) {
            response.sendRedirect("addProduct?error=Invalid%20Input");
            return;
        }

        InventoryDTO newItem = new InventoryDTO();
        newItem.setFoodId(foodId);
        newItem.setFoodName(foodName);
        newItem.setQuantity(quantity);
        newItem.setExpDate(expDate);
        newItem.setPrice(price);
        newItem.setSurplus(surplus);

        try {
            retailerBusinessLogic.addInventory(newItem);
        } catch (ClassNotFoundException ex) {
            log(ex.getMessage());
        }

        response.sendRedirect("retailer");
    }
}