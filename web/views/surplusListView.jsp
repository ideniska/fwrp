<%-- 
    Document   : surplusList
    Created on : Jul. 11, 2024, 7:13:45 p.m.
    Author     : denissakhno
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.InventoryDTO" %>
<%@ page import="dataAccessLayer.InventoryDaoImpl" %>
<!DOCTYPE html>
<html>
<head>
    <title>Surplus List</title>
</head>
<body>
    <h1>Surplus List</h1>
    <table border="1">
        <tr>
            <th>Food ID</th>
            <th>Food Name</th>
            <th>Quantity</th>
            <th>Expiration Date</th>
            <th>Price</th>
        </tr>
        <%
            InventoryDaoImpl dao = new InventoryDaoImpl();
            List<InventoryDTO> surplusList = dao.getSurplusInventory();
            for (InventoryDTO item : surplusList) {
        %>
        <tr>
            <td><%= item.getFoodId() %></td>
            <td><%= item.getFoodName() %></td>
            <td><%= item.getQuantity() %></td>
            <td><%= item.getExpDate() %></td>
            <td><%= item.getPrice() %></td>
        </tr>
        <% } %>
    </table>
</body>
</html>
