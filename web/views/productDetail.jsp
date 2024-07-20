<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.InventoryDTO" %>
<!DOCTYPE html>
<html>
<head>
    <title>Product Details</title>
</head>
<body>
    <h1>Product Details</h1>
    <%
        InventoryDTO product = (InventoryDTO) request.getAttribute("product");
        if (product != null) {
    %>
    <form action="updateProduct" method="post">
        <input type="hidden" name="action" value="update">
        <table border="1">
            <tr>
                <th>Food ID</th>
                <td><%= product.getFoodId() %></td>
                <input type="hidden" name="foodId" value="<%= product.getFoodId() %>">
            </tr>
            <tr>
                <th>Food Name</th>
                <td><input type="text" name="foodName" value="<%= product.getFoodName() %>"></td>
            </tr>
            <tr>
                <th>Quantity</th>
                <td><input type="number" name="quantity" value="<%= product.getQuantity() %>" min="0"></td>
            </tr>
            <tr>
                <th>Expiration Date</th>
                <td><input type="date" name="expDate" value="<%= product.getExpDate() %>"></td>
            </tr>
<tr>
                <th>Surplus</th>
                <td>
                    <select name="surplus">
                        <option value="0" <%= product.getSurplus() == 0 ? "selected" : "" %>>0</option>
                        <option value="1" <%= product.getSurplus() == 1 ? "selected" : "" %>>1</option>
                    </select>
                </td>
            </tr>
            <tr>
            <tr>
                <th>Price</th>
                <td><input type="number" step="0.01" name="price" value="<%= product.getPrice() %>" min="0"></td>
            </tr>
        </table>
        <input type="submit" value="Update">
    </form>
    <form action="updateProduct" method="post" onsubmit="return confirm('Are you sure you want to delete this product?');">
        <input type="hidden" name="action" value="delete">
        <input type="hidden" name="foodId" value="<%= product.getFoodId() %>">
        <input type="submit" value="Delete">
    </form>
    <a href="/FWRP/retailer">Back to Inventory</a>
    <%
        } else {
    %>
    <p>Product not found.</p>
    <%
        }
    %>
</body>
</html>