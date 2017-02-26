<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Pay</title>
</head>
<body>
<tr>
    <td>TotalPrice</td>
</tr>

<tr>
    <td>${order.totalPrice}</td>
</tr>
<form name="CheckCardForm" method="POST" action="<c:url value="./pay"/>">
    <input type="hidden" name="orderId" value="${order.orderId}"/>
    <table>
        <tr>
            <td>CardNumber:</td>
            <td><input type="text" pattern="${cardNumberFormatRegExp}" name="cardNumber" size="30"
                       placeholder="${cardNumberInputPlaceholder}" required/></td>
        </tr>
    </table>
    <input type="submit" name="newStatus" value="pay"/>
</form>
${operationMessage}<br/>
<a href="<c:url value="/clients/${login}"/>">Back to StartPage</a><br/>
<a href="<c:url value="../orders"/>">Back to Client Orders</a><br/>
<a href="<c:url value="/clients/${login}/creditcards/checkcard"/>">Check Credit Card</a> <br/>
<a href="<c:url value="/login" />">Logout</a>
</body>
</html>