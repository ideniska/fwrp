/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import businesslayer.CharityBusinessLogic;
import dataAccessLayer.InventoryDaoImpl;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.InventoryDTO;
import model.UserType;
import util.AuthUtils;

/**
 *
 * @author denissakhno
 */


public class CharityServlet extends HttpServlet {
    private CharityBusinessLogic charityBusinessLogic;

    @Override
    public void init() {
        this.charityBusinessLogic = new CharityBusinessLogic();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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