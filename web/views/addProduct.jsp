<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add New Product</title>
    <script>
        function validateForm() {
            const price = parseFloat(document.getElementById("price").value);
            const quantity = parseInt(document.getElementById("quantity").value);
            const expDate = new Date(document.getElementById("expDate").value);
            const currentDate = new Date();

            if (price <= 0) {
                alert("Price must be greater than 0");
                return false;
            }

            if (quantity <= 0) {
                alert("Quantity must be greater than 0");
                return false;
            }

            if (expDate <= currentDate) {
                alert("Expiration date must be later than the current date");
                return false;
            }

            return true;
        }
    </script>
</head>
<body>
    <h1>Add New Product</h1>
    <form action="addProduct" method="post" onsubmit="return validateForm();">
        <label for="foodId">Food ID:</label><br>
        <input type="text" id="foodId" name="foodId" value="<%= request.getAttribute("latestFoodId") %>" readonly><br>
        <label for="foodName">Food Name:</label><br>
        <input type="text" id="foodName" name="foodName"><br>
        <label for="quantity">Quantity:</label><br>
        <input type="text" id="quantity" name="quantity"><br>
        <label for="expDate">Expiration Date (yyyy-mm-dd):</label><br>
        <input type="text" id="expDate" name="expDate"><br>
        <label for="price">Price:</label><br>
        <input type="text" id="price" name="price"><br>
        <label for="surplus">Surplus:</label><br>
        <select id="surplus" name="surplus">
            <option value="0">No</option>
            <option value="1">Yes</option>
        </select><br><br>
        <input type="submit" value="Add Product">
    </form>
    <br>
    <form action="retailer" method="get">
        <input type="submit" value="Back to Inventory">
    </form>
</body>
</html>