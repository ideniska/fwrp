    <%-- 
    Document   : purchasePage
    Created on : Jul. 11, 2024, 7:14:37 p.m.
    Author     : Yuchen Wang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
<%@page import="model.InventoryDTO" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Purchase item</title>
    </head>
    <body> 
        <h1>Purchase items</h1>
        <form method ="post" action ="Consumer">
            <table border ="1">
                <tr>
                    <th>Food ID</th>
                    <th>Food name</th>
                    <th>Expiration date</th>
                    <th>Original price</th>
                    <th>Purchase price</th>
                    <th>Quantity</th>
                    <th>Buy</th>
                </tr>
                <%
                    List<InventoryDTO> inventoryList = (List<InventoryDTO>)request.getAttribute("inventoryList");
                    for(InventoryDTO item : inventoryList){
                        double purchasePrice = item.getPrice() *0.5;
                %>
                <tr>
                    <td><%=item.getFoodId()  %></td>
                    <td><%=item.getFoodName() %></td>
                    <td><%=item.getExpDate() %></td>
                    <td><%=item.getPrice() %></td>
                    <td><%=purchasePrice %></td>
                    <td>
                        <input type ="number" name="quantity_<%=item.getFoodId()%>" min="1" max ="<%=item.getQuantity() %>">
                        <input type ="hidden" name="price_<%= item.getFoodId()%>" value ="<%=purchasePrice %>"> 
                        <input type="hidden" name="foodName_<%= item.getFoodId() %>" value="<%= item.getFoodName() %>">
                    </td>
                    <td><input type="checkbox" name="foodId" value="<%= item.getFoodId() %>"></td>
                </tr>
                <% } %>
            </table>
            <input type="hidden" name="userId" value="1"><!--just static for test, it will be add session to get real user-->
            <input type="submit" value="Purchase">
        </form>
        <p><%=request.getAttribute("message") !=null ? request.getAttribute("message") :""%></p>
            
    <form action="<%=request.getContextPath()%>/logout" method="post">
        <input type="submit" value="Log Out">
    </form>

    </body>
</html>
