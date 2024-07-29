<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Registration Error</title>
</head>
<body>
    <h1>Registration Error</h1>
    <p>The email address you entered is already registered. Please try registering with a different email address or <a href="<%=request.getContextPath()%>/login">login</a> if you already have an account.</p>
    <a href="<%=request.getContextPath()%>/register">Back to Register</a>
</body>
</html>
