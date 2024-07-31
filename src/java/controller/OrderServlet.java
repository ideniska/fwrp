package controller;

import businesslayer.OrderBusinessLogic;
import model.UserDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * OrderServlet handles user order-related requests, including viewing and updating user details.
 * 
 */
public class OrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrderBusinessLogic orderBusinessLogic;

    /**
     * Initializes the OrderBusinessLogic instance.
     */
    public OrderServlet() {
        this.orderBusinessLogic = new OrderBusinessLogic();
    }

    /**
     * Handles the HTTP GET method.
     * Retrieves user details based on userId and forwards the request to the orderView.jsp view.
     *
     * @param request  the HttpServletRequest object that contains the request the client made to the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if the request could not be handled
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        try {
            UserDTO user = orderBusinessLogic.getUserById(userId);
            request.setAttribute("user", user);
            request.getRequestDispatcher("/views/orderView.jsp").forward(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
    }

    /**
     * Handles the HTTP POST method.
     * Updates user details based on the form input and redirects to a confirmation page.
     *
     * @param request  the HttpServletRequest object that contains the request the client made to the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if the request could not be handled
     * @throws IOException      if an input or output error is detected when the servlet handles the POST request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String cardNumber = request.getParameter("cardNumber");

            UserDTO user = new UserDTO();
            user.setUserId(userId);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPhone(phone);
            user.setAddress(address);

            orderBusinessLogic.updateUser(user);

            response.sendRedirect("confirmation"); 
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
    }
}