<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Welcome, ${user.login}</title>
</head>
<body>
<h2>${user.firstName} ${user.lastName}</h2>
<h2>${user.login}</h2>
<a href="<c:url value="./${user.login}/orders/makeorder"/>">Make Order</a> <br/>
<a href="<c:url value="./${user.login}/creditcards/checkcard"/>">Check Credit Card Amount</a> <br/>
<a href="<c:url value="./${user.login}/creditcards/addcard"/>">Add Credit Card</a> <br/>
<a href="<c:url value="./${user.login}/orders"/>">Show Orders List</a> <br/>
<a href="<c:url value="/login" />">Logout</a>
</body>
</html>
