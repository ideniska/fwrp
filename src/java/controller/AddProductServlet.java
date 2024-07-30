package controller;

import businesslayer.RetailerBusinessLogic;
import dataAccessLayer.DataSource;
import model.InventoryDTO;
import model.UserDTO;
import model.UserType;
import notification.EmailObserver;
import notification.NotificationService;
import notification.SMSObserver;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddProductServlet extends HttpServlet {

    private NotificationService notificationService;

    public AddProductServlet() {
        this.notificationService = new NotificationService();
    }

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

        // Notification logic
        notificationService.clearObservers();  // Clear previous observers before adding new ones
        int smsCount = 0;
        int emailCount = 0;

        try (Connection connection = DataSource.getConnection()) {
            String sql = "SELECT * FROM User WHERE location = ? AND food_preference = ? AND user_type = 1";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, location);
                preparedStatement.setString(2, foodPreference);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int notifications = resultSet.getInt("notifications");
                        String email = resultSet.getString("email");
                        String phone = resultSet.getString("phone");

                        if (notifications == 2) {  // PHONE
                            notificationService.registerObserver(new SMSObserver(phone));
                            smsCount++;
                        } else if (notifications == 3) {  // EMAIL
                            notificationService.registerObserver(new EmailObserver(email));
                            emailCount++;
                        } else if (notifications == 4) {  // BOTH
                            notificationService.registerObserver(new SMSObserver(phone));
                            notificationService.registerObserver(new EmailObserver(email));
                            smsCount++;
                            emailCount++;
                        } else {
                            log("Unknown notification type for user: " + email);
                        }
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        String message = "New product added: " + foodName + " (" + foodPreference + ", " + location + ")";
        System.out.println(message);

        // Print out the number of notifications sent
        System.out.println("Notifications sent: ");
        System.out.println("SMS: " + smsCount);
        System.out.println("Email: " + emailCount);

        response.sendRedirect("retailer");
    }

}
