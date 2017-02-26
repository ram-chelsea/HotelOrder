<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Welcome, Admin</title>
</head>
<body>
<h2>${user.firstName} ${user.lastName}</h2>
<h2>${user.login}</h2>
<a href="<c:url value="./${user.login}/clients"/>">Show Clients List</a> <br/>
<a href="<c:url value="./${user.login}/orders"/>">Show Orders List</a> <br/>
<a href="<c:url value="./${user.login}/rooms"/>">Show Rooms List</a> <br/>
<a href="<c:url value="/login" />">Logout</a>
</body>
</html>