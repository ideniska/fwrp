package controller;

import dataAccessLayer.UserDaoImpl;
import model.UserDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginLogicServlet extends HttpServlet {

    private UserDaoImpl userDao;

    @Override
    public void init() throws ServletException {
        userDao = new UserDaoImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDTO user = userDao.authenticateUser(email, password);

        if (user != null) {
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("userId", user.getUserId()); // Store userId in session

            switch (user.getUserType()) {
                case 1:
                    response.sendRedirect(request.getContextPath() + "/consumer");
                    break;
                case 2:
                    response.sendRedirect(request.getContextPath() + "/charity");
                    break;
                case 3:
                    response.sendRedirect(request.getContextPath() + "/retailer");
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/login");
                    break;
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/loginError");
        }
    }
}
