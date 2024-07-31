package controller;

import dataAccessLayer.UserDaoImpl;
import model.UserDTO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * UpdateUserServlet handles the updating of user profile details.
 * 
 * author Berkay
 */
public class UpdateUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles the HTTP POST method.
     * Updates the user details in the database and refreshes the session with the updated user information.
     *
     * @param request  the HttpServletRequest object that contains the request the client made to the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if the request could not be handled
     * @throws IOException      if an input or output error is detected when the servlet handles the POST request
     */
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
        String foodPreference = request.getParameter("foodPreference");
        Integer notifications = request.getParameter("notifications") != null ? Integer.valueOf(request.getParameter("notifications")) : null;
        String orgName = request.getParameter("org_name");

        user.setAddress(address);
        user.setPhone(phone);
        user.setLocation(location);
        user.setFoodPreference(foodPreference);
        user.setNotifications(notifications);

        if (user.getUserType() == 2) { 
            user.setOrgName(orgName);
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