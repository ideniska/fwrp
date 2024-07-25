<%-- 
    Document   : charityView
    Created on : Jul 23, 2024, 9:24:14 p.m.
    Author     : dmlop
--%>
<%@page import="model.InventoryDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Charity Inventory List</title>
</head>
<body>
    <h1>Charity Inventory</h1>
    <form action="charity" method="post">
        <table border="1">
            <thead>
                <tr>
                    <th>Food ID</th>
                    <th>Food Name</th>
                    <th>Expiration Date</th>
                    <th>Original Price</th>
                    <th>Charity Price</th>
                    <th>Quantity</th>
                    <th>Claim</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<InventoryDTO> charityList = (List<InventoryDTO>) request.getAttribute("inventoryItems");
                    if (charityList != null && !charityList.isEmpty()) {
                        boolean itemsAvailable = false;
                        for (InventoryDTO item : charityList) {
                            if (item.getQuantity() > 0) {
                                double charityPrice = item.getPrice() * 0.5;
                                itemsAvailable = true;
                %>
                                <tr>
                                    <td><%= item.getFoodId() %></td>
                                    <td><%= item.getFoodName() %></td>
                                    <td><%= item.getExpDate() %></td>
                                    <td><%= item.getPrice() %></td>
                                    <td><%= charityPrice %></td>
                                    <td><%= item.getQuantity() %></td>
                                    <td>
                                        <input type="number" name="claimQuantity_<%= item.getFoodId() %>" min="1" max="<%= item.getQuantity() %>" placeholder="0">
                                        <input type="hidden" name="price_<%= item.getFoodId() %>" value="<%= charityPrice %>">
                                        <input type="hidden" name="foodName_<%= item.getFoodId() %>" value="<%= item.getFoodName() %>">
                                    </td>
                                    <td><input type="checkbox" name="foodId" value="<%= item.getFoodId() %>"></td>
                                </tr>
                <%
                            }
                        }
                        if (!itemsAvailable) {
                %>
                            <tr>
                                <td colspan="7">No items available for claiming.</td>
                            </tr>
                <%
                        }
                    } else {
                %>
                        <tr>
                            <td colspan="7">No items found.</td>
                        </tr>
                <%
                    }
                %>
            </tbody>
        </table>
        <input type="hidden" name="userId" value="1"><!-- just static for test, it will be add session to get real user -->
        <input type="submit" value="Claim Selected Items">
    </form>
    <div id="responseMessage"><%= request.getAttribute("message") != null ? request.getAttribute("message") : "" %></div>
</body>
</html>
