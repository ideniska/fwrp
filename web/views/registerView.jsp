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
        Email: <input type="email" name="email" required title="Please enter a valid email address"><br>
        Password: <input type="password" name="password" required><br>
        Confirm Password: <input type="password" name="confirm_password" required><br>
        User Type: 
        <select name="user_type" required>
            <option value="1">Customer</option>
            <option value="2">Charity</option>
            <option value="3">Retailer</option>
        </select><br>
        <input type="submit" value="Register">
    </form>
    <p>Already have an account? <a href="<%=request.getContextPath()%>/login">Login</a></p>
</body>
</html>
