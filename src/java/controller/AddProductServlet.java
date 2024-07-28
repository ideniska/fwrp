package controller;

import businesslayer.RetailerBusinessLogic;
import model.InventoryDTO;
import model.UserDTO;
import model.UserType;
import util.AuthUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.SQLException;

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
        String foodPreference = request.getParameter("foodPreference");
        String location = request.getParameter("location");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date expDate = null;

        try {
            expDate = formatter.parse(expDateString);
        } catch (ParseException e) {
            log("Date parsing error: " + e.getMessage());
            response.sendRedirect("addProduct?error=Invalid%20Date%20Format");
            return;
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
        newItem.setFoodPreference(foodPreference);
        newItem.setLocation(location);

        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
        int userId = user.getUserId();

        try {
            retailerBusinessLogic.addInventory(newItem, userId);
        } catch (ClassNotFoundException ex) {
            log(ex.getMessage());
            response.sendRedirect("addProduct?error=Database%20Error");
            return;
        }

        response.sendRedirect("retailer");
    }
}
