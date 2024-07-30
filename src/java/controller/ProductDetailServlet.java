
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

//@WebServlet("/detail")
public class ProductDetailServlet extends HttpServlet {
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
