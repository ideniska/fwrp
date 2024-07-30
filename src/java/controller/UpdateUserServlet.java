package controller;

import dataAccessLayer.UserDaoImpl;
import model.UserDTO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class UpdateUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        UserDTO user = (UserDTO) session.getAttribute("user");

        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String location = request.getParameter("location");
        Integer communication = Integer.valueOf(request.getParameter("communication"));
        String foodPreference = request.getParameter("foodPreference");
        Integer notifications = request.getParameter("notifications") != null ? Integer.valueOf(request.getParameter("notifications")) : null;
        String orgName = request.getParameter("org_name");

        user.setAddress(address);
        user.setPhone(phone);
        user.setLocation(location);
        user.setCommunication(communication);

        if (user.getUserType() == 1) {
            user.setFoodPreference(foodPreference);
            user.setNotifications(notifications);
        } else if (user.getUserType() == 2) {
            user.setOrgName(orgName);  // Set orgName for Charities
            user.setNotifications(notifications);
        } else if (user.getUserType() == 3) {
            // For Retailers, no additional fields to set
        }

        UserDaoImpl userDao = new UserDaoImpl();
        try {
            userDao.updateUser(user);
            session.setAttribute("user", user);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/manageProfile");
    }
}
