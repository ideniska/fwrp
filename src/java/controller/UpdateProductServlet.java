package controller;

import businesslayer.RetailerBusinessLogic;
import model.InventoryDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * UpdateProductServlet handles the updating and deleting of products in the retailer's inventory.
 * 
 * author Denis Sakhno
 */
public class UpdateProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles the HTTP POST method.
     * Processes the update or delete actions for a product in the inventory.
     *
     * @param request  the HttpServletRequest object that contains the request the client made to the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if the request could not be handled
     * @throws IOException      if an input or output error is detected when the servlet handles the POST request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        RetailerBusinessLogic retailerBusinessLogic = new RetailerBusinessLogic();

        if ("update".equals(action)) {
            int foodId = Integer.parseInt(request.getParameter("foodId"));
            String foodName = request.getParameter("foodName");
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            Date expDate = null;
            try {
                expDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("expDate"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Double price = Double.parseDouble(request.getParameter("price"));
            int surplus = Integer.parseInt(request.getParameter("surplus"));

            InventoryDTO product = new InventoryDTO();
            product.setFoodId(foodId);
            product.setFoodName(foodName);
            product.setQuantity(quantity);
            product.setExpDate(expDate);
            product.setPrice(price);
            product.setSurplus(surplus); 

            try {
                retailerBusinessLogic.updateInventory(product);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            response.sendRedirect("productDetail?foodId=" + foodId);
        } else if ("delete".equals(action)) {
            int foodId = Integer.parseInt(request.getParameter("foodId"));
            try {
                retailerBusinessLogic.deleteInventory(foodId);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            response.sendRedirect("/FWRP/retailer");
        }
    }
}