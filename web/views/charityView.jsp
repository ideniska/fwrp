<%@page import="dataAccessLayer.CharityInventoryDaoImpl"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.InventoryDTO" %>
<html>
<head>
    <title>Charity Inventory List</title>
</head>
<body>
    <h1>Charity Inventory</h1>
    <form action="${pageContext.request.contextPath}/charityView" method="post">
        <table border="1">
            <thead>
                <tr>
                    <th>Food ID</th>
                    <th>Food Name</th>
                    <th>Quantity</th>
                    <th>Expiration Date</th>
                    <th>Price</th>
                    <th>Claim Quantity</th>
                </tr>
            </thead>
            <tbody>
                <%
                    CharityInventoryDaoImpl dao = new CharityInventoryDaoImpl();
                    List<InventoryDTO> charityList = dao.getCharityInventory();
                    if (charityList != null && !charityList.isEmpty()) {
                        for (InventoryDTO item : charityList) {
                %>
                            <tr>
                                <td><%= item.getFoodId() %></td>
                                <td><%= item.getFoodName() %></td>
                                <td><%= item.getQuantity() %></td>
                                <td><%= item.getExpDate() %></td>
                                <td><%= item.getPrice() %></td>
                                <td>
                                    <input type="number" name="claimQuantity_<%= item.getFoodId() %>" min="1" max="<%= item.getQuantity() %>" placeholder="0">
                                </td>
                            </tr>
                <%
                        }
                    } else {
                %>
                        <tr>
                            <td colspan="6">No items found.</td>
                        </tr>
                <%
                    }
                %>
            </tbody>
        </table>
        <input type="submit" value="Claim Selected Items">
    </form>
</body>
</html>
