package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ManageProfileServlet handles the display of the profile management page.
 * 
 * author Berkay
 */
public class ManageProfileServlet extends HttpServlet {
    
    /**
     * Handles the HTTP GET method.
     * Forwards the request to the manageProfile.jsp view.
     *
     * @param request  the HttpServletRequest object that contains the request the client made to the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if the request could not be handled
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("views/manageProfile.jsp").forward(request, response);
    }
}