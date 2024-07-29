<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
</head>
<body>
    <h1>Register</h1>
    <form action="<%=request.getContextPath()%>/registerUser" method="post">
        First Name: <input type="text" name="first_name" required><br>
        Last Name: <input type="text" name="last_name" required><br>
        Org Name: <input type="text" name="org_name"><br>
        Address: <input type="text" name="address"><br>
        Phone: <input type="text" name="phone"><br>
        Email: <input type="text" name="email" required><br>
        Password: <input type="password" name="password" required><br>
        User Type: 
        <select name="user_type">
            <option value="1">Customer</option>
            <option value="2">Charity</option>
            <option value="3">Retailer</option>
        </select><br>
        Location: 
        <select name="location">
            <option value="Ottawa">Ottawa</option>
            <option value="Kanata">Kanata</option>
        </select><br>
        Communication: 
        <select name="communication">
            <option value="1">No</option>
            <option value="2">Phone</option>
            <option value="3">Email</option>
            <option value="4">Both</option>
        </select><br>
        Food Preference: 
        <select name="food_preference">
            <option value="Vegan">Vegan</option>
            <option value="Not Vegan">Not Vegan</option>
        </select><br>
        Notifications: 
        <select name="notifications">
            <option value="1">No</option>
            <option value="2">Phone</option>
            <option value="3">Email</option>
            <option value="4">Both</option>
        </select><br>
        <input type="submit" value="Register">
    </form>
    <p>Already have an account? <a href="<%=request.getContextPath()%>/login">Login</a></p>
</body>
</html>
