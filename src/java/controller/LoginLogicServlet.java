package controller;

import dataAccessLayer.UserDaoImpl;
import model.UserDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * LoginLogicServlet handles the login logic for the application.
 * Authenticates the user and redirects them to the appropriate dashboard based on their user type.
 * 
 * author Berkay
 */
public class LoginLogicServlet extends HttpServlet {

    private UserDaoImpl userDao;

    /**
     * Initializes the UserDaoImpl instance.
     */
    @Override
    public void init() throws ServletException {
        userDao = new UserDaoImpl();
    }

    /**
     * Handles the HTTP POST method.
     * Authenticates the user and redirects them to the appropriate dashboard based on their user type.
     *
     * @param request  the HttpServletRequest object that contains the request the client made to the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if the request could not be handled
     * @throws IOException      if an input or output error is detected when the servlet handles the POST request
     */
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