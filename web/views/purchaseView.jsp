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
    <script>
            function updateSummary() {
                let totalPrice = 0;
                let checkboxes = document.querySelectorAll('input[name="foodId"]:checked');
                checkboxes.forEach(function(checkbox) {
                    let foodId = checkbox.value;
                    let quantityInput = document.querySelector('input[name="quantity_' + foodId + '"]');
                    let priceInput = document.querySelector('input[name="price_' + foodId + '"]');
                    let quantity = parseInt(quantityInput.value) || 0;
                    let price = parseFloat(priceInput.value) || 0;
                    totalPrice += quantity * price;
                });

                let credit = parseFloat(document.getElementById('credit').innerText) || 0;
                let actualPayment = totalPrice - credit;

                document.getElementById('totalPrice').innerText = totalPrice.toFixed(2);
                document.getElementById('actualPayment').innerText = actualPayment.toFixed(2);
            }

             function handlePurchase() {

            }
        </script>
    </head>
    <body>
        <h1>Purchase items</h1>
        <form method ="post" action ="Consumer" onsubmit="return handlePurchase()">
            <table border ="1">
                <tr>
                    <th>Food ID</th>
                    <th>Food name</th>
                    <th>Expiration date</th>
                    <th>In stock</th>
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
                    <td><%=item.getQuantity() %></td>
                    <td><%=item.getPrice() %></td>
                    <td><%=purchasePrice %></td>
                    <td>
                        <input type ="number" name="quantity_<%=item.getFoodId()%>" min="1" max ="<%=item.getQuantity() %>" oninput="updateSummary()">
                        <input type ="hidden" name="price_<%= item.getFoodId()%>" value ="<%=purchasePrice %>">
                        <input type="hidden" name="foodName_<%= item.getFoodId() %>" value="<%= item.getFoodName() %>">
                    </td>
                    <td><input type="checkbox" name="foodId" value="<%= item.getFoodId() %>" onclick="updateSummary()"></td>
                </tr>
                <% } %>
            </table>
            <input type="hidden" name="userId" value="1">
            <!--<input type="hidden" name="userId" value="<%= session.getAttribute("userId") %>">dynamic to get userID-->
            <h2>Summary</h2>
            <table border="1">
                <tr>
                    <th>Total Price</th>
                    <td id="totalPrice"><%= request.getAttribute("totalPrice") != null ? request.getAttribute("totalPrice") : 0 %></td>
                </tr>
                <tr>
                    <th>Credit</th>
                    <td id="credit"><%= request.getAttribute("credit") != null ? request.getAttribute("credit") : 0 %></td>
                </tr>
                <tr>
                    <th>Actual Payment</th>
                    <td id="actualPayment"><%= request.getAttribute("actualPayment") != null ? request.getAttribute("actualPayment") : 0 %></td>
                </tr>
            </table>
            <input type="submit" value="Purchase">
        </form>
    </table>
        <p id="message"><%= request.getAttribute("message") != null ? request.getAttribute("message") : "" %></p>

    <a href="<%=request.getContextPath()%>/manageProfile">Manage Profile</a>

    <form action="<%=request.getContextPath()%>/logout" method="post">
        <input type="submit" value="Log Out">
    </form>

    </body>
</html>
