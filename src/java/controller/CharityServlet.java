/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import businesslayer.CharityBusinessLogic;
import businesslayer.CheckoutBusinessLogic;
import static com.mysql.cj.conf.PropertyKey.logger;
import model.InventoryDTO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.TransactionItemDTO;
import model.UserTransactionDTO;
import model.UserType;
import util.AuthUtils;

/**
 *
 * @author denissakhno
 */

public class CharityServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CharityBusinessLogic charityBusinessLogic;
    private static final Logger logger = Logger.getLogger(CharityServlet.class.getName());

    @Override
    public void init() {
        this.charityBusinessLogic = new CharityBusinessLogic();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthUtils.checkUserType(request, UserType.CHARITY)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        try {
            List<InventoryDTO> inventoryItems = charityBusinessLogic.getCharityInventory();
            request.setAttribute("inventoryItems", inventoryItems);
            RequestDispatcher dispatcher = request.getRequestDispatcher("views/charityView.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException("Error retrieving charity inventory items", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthUtils.checkUserType(request, UserType.CHARITY)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            if (paramName.startsWith("claimQuantity_")) {
                int foodId = Integer.parseInt(paramName.substring(14));
                int claimQuantity = Integer.parseInt(request.getParameter(paramName));
                if (claimQuantity > 0) {
                    try {
                        charityBusinessLogic.claimItem(foodId, claimQuantity);
                    } catch (SQLException | ClassNotFoundException e) {
                        throw new ServletException("Error claiming item", e);
                    }
                }
            }
        }
        response.sendRedirect("/charityView");
    }
}