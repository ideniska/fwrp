<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.InventoryDTO" %>
<!DOCTYPE html>
<html>
<head>
    <title>Retailer Inventory</title>
</head>
<body>
    <h1>Retailer Inventory</h1>
    <table border="1">
        <tr>
            <th>Food ID</th>
            <th>Food Name</th>
            <th>Quantity</th>
            <th>Expiration Date</th>
            <th>Price</th>
        </tr>
        <%
            List<InventoryDTO> inventoryList = (List<InventoryDTO>) request.getAttribute("inventoryList");
            if (inventoryList != null) {
                for (InventoryDTO item : inventoryList) {
        %>
        <tr>
            <td><a href="productDetail?foodId=<%= item.getFoodId() %>"><%= item.getFoodId() %></a></td>
            <td><%= item.getFoodName() %></td>
            <td><%= item.getQuantity() %></td>
            <td><%= item.getExpDate() %></td>
            <td><%= item.getPrice() %></td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="5">No inventory items available.</td>
        </tr>
        <%
            }
        %>
    </table>
</body>
</html>