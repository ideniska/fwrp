/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.UserDTO;
import dataAccessLayer.UserDaoImpl;

public class RegisterUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form parameters
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String orgName = request.getParameter("org_name"); // Not used in the UserDTO
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        int userType = Integer.parseInt(request.getParameter("user_type"));
        String location = request.getParameter("location");
        int communication = Integer.parseInt(request.getParameter("communication"));
        String foodPreference = request.getParameter("food_preference");
        int notifications = Integer.parseInt(request.getParameter("notifications"));

        // Create a UserDTO object
        UserDTO user = new UserDTO();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAddress(address);
        user.setPhone(phone);
        user.setEmail(email);
        user.setPassword(password);
        user.setUserType(userType);
        user.setLocation(location);
        user.setCommunication(communication);
        user.setFoodPreference(foodPreference);
        user.setNotifications(notifications);

        // Use UserDaoImpl to add the user to the database
        UserDaoImpl userDao = new UserDaoImpl();
        try {
            userDao.addUser(user);
            // Redirect to a success page or another page as needed
            response.sendRedirect(request.getContextPath() + "/views/loginView.jsp");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            // Handle the exception, possibly redirect to an error page
            response.sendRedirect("error.jsp");
        }
    }
}
