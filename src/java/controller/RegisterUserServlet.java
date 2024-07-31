package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.UserDTO;
import dataAccessLayer.UserDaoImpl;

/**
 * RegisterUserServlet handles the user registration process.
 * 
 * author Berkay
 */
public class RegisterUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles the HTTP POST method.
     * Processes the user registration form and adds the user to the database.
     *
     * @param request  the HttpServletRequest object that contains the request the client made to the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if the request could not be handled
     * @throws IOException      if an input or output error is detected when the servlet handles the POST request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form parameters
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");
        int userType = Integer.parseInt(request.getParameter("user_type"));

        // Validate passwords
        if (!password.equals(confirmPassword)) {
            response.sendRedirect(request.getContextPath() + "/registerError?error=passwordMismatch");
            return;
        }

        // Create a UserDTO object
        UserDTO user = new UserDTO();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password); 
        user.setUserType(userType);

        // Use UserDaoImpl to add the user to the database
        UserDaoImpl userDao = new UserDaoImpl();
        try {
            boolean userAdded = userDao.addUser(user);
            if (userAdded) {
                // Redirect to a success page or another page as needed
                response.sendRedirect(request.getContextPath() + "/login");
            } else {
                // Email already exists, redirect to the error page
                response.sendRedirect(request.getContextPath() + "/registerError?error=emailExists");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            // Handle the exception, possibly redirect to an error page
            response.sendRedirect("error.jsp");
        }
    }
}