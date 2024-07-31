package controller;

import businesslayer.RetailerBusinessLogic;
import model.InventoryDTO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import model.UserType;
import util.AuthUtils;

/**
 * ProductDetailServlet handles the retrieval and display of product details for retailers.
 * 
 * author Denis Sakhno
 */
public class ProductDetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles the HTTP GET method.
     * Retrieves product details based on the foodId and forwards the request to the productDetail.jsp view.
     *
     * @param request  the HttpServletRequest object that contains the request the client made to the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if the request could not be handled
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!AuthUtils.checkUserType(request, UserType.RETAILER)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        String foodId = request.getParameter("foodId");

        RetailerBusinessLogic retailerBusinessLogic = new RetailerBusinessLogic();

        InventoryDTO selectedProduct = null;
        try {
            selectedProduct = retailerBusinessLogic.getInventoryById(Integer.parseInt(foodId));
        } catch (SQLException | ClassNotFoundException ex) {
            log(ex.getMessage());
        }

        if (selectedProduct != null) {
            request.setAttribute("product", selectedProduct);
            request.getRequestDispatcher("views/productDetail.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
        }
    }
}