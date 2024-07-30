<%@ page import="model.UserDTO" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="javax.servlet.http.HttpServletResponse" %>
<%@ page import="javax.servlet.ServletException" %>
<%@ page import="java.io.IOException" %>
<%
    if (session == null || session.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }

    UserDTO user = (UserDTO) session.getAttribute("user");
    int userType = user.getUserType();
    String mainPageUrl;
    switch (userType) {
        case 1:
            mainPageUrl = request.getContextPath() + "/consumer";
            break;
        case 2:
            mainPageUrl = request.getContextPath() + "/charity";
            break;
        case 3:
            mainPageUrl = request.getContextPath() + "/retailer";
            break;
        default:
            mainPageUrl = request.getContextPath() + "/login";
            break;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Profile</title>
</head>
<body>
    <h1>Manage Profile</h1>
    <a href="<%= mainPageUrl %>">Back to Main Page</a>
    <form action="<%=request.getContextPath()%>/updateUser" method="post">
        <% if (userType == 1) { %>
            Address: <input type="text" name="address" value="<%= user.getAddress() %>"><br>
            Phone: <input type="text" name="phone" value="<%= user.getPhone() %>"><br>
            Location: 
            <select name="location">
                <option value="Ottawa" <%= "Ottawa".equals(user.getLocation()) ? "selected" : "" %>>Ottawa</option>
                <option value="Kanata" <%= "Kanata".equals(user.getLocation()) ? "selected" : "" %>>Kanata</option>
            </select><br>
            Food Preference: 
            <select name="foodPreference">
                <option value="Vegan" <%= "Vegan".equals(user.getFoodPreference()) ? "selected" : "" %>>Vegan</option>
                <option value="Not Vegan" <%= "Not Vegan".equals(user.getFoodPreference()) ? "selected" : "" %>>Not Vegan</option>
            </select><br>
            Notifications: 
            <select name="notifications">
                <option value="1" <%= user.getNotifications() != null && user.getNotifications() == 1 ? "selected" : "" %>>No</option>
                <option value="2" <%= user.getNotifications() != null && user.getNotifications() == 2 ? "selected" : "" %>>Phone</option>
                <option value="3" <%= user.getNotifications() != null && user.getNotifications() == 3 ? "selected" : "" %>>Email</option>
                <option value="4" <%= user.getNotifications() != null && user.getNotifications() == 4 ? "selected" : "" %>>Both</option>
            </select><br>
        <% } else if (userType == 2) { %>
            Organization Name: <input type="text" name="org_name" value="<%= user.getOrgName() %>"><br>
            Address: <input type="text" name="address" value="<%= user.getAddress() %>"><br>
            Phone: <input type="text" name="phone" value="<%= user.getPhone() %>"><br>
            Location: 
            <select name="location">
                <option value="Ottawa" <%= "Ottawa".equals(user.getLocation()) ? "selected" : "" %>>Ottawa</option>
                <option value="Kanata" <%= "Kanata".equals(user.getLocation()) ? "selected" : "" %>>Kanata</option>
            </select><br>
            Notifications: 
            <select name="notifications">
                <option value="1" <%= user.getNotifications() != null && user.getNotifications() == 1 ? "selected" : "" %>>No</option>
                <option value="2" <%= user.getNotifications() != null && user.getNotifications() == 2 ? "selected" : "" %>>Phone</option>
                <option value="3" <%= user.getNotifications() != null && user.getNotifications() == 3 ? "selected" : "" %>>Email</option>
                <option value="4" <%= user.getNotifications() != null && user.getNotifications() == 4 ? "selected" : "" %>>Both</option>
            </select><br>
        <% } else if (userType == 3) { %>
            Address: <input type="text" name="address" value="<%= user.getAddress() %>"><br>
            Phone: <input type="text" name="phone" value="<%= user.getPhone() %>"><br>
            Location: 
            <select name="location">
                <option value="Ottawa" <%= "Ottawa".equals(user.getLocation()) ? "selected" : "" %>>Ottawa</option>
                <option value="Kanata" <%= "Kanata".equals(user.getLocation()) ? "selected" : "" %>>Kanata</option>
            </select><br>
        <% } %>
        <input type="submit" value="Update">
    </form>
</body>
</html>
