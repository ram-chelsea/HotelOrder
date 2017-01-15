<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
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
<form name="CheckCardForm" method="POST" action="controller">
    <input type="hidden" name="command" value="pay"/>
    <input type="hidden" name="orderId" value="${order.orderId}"/>
    <table>
        <tr>
            <td>CardNumber:</td>
            <td><input type="text" pattern="${cardNumberFormatRegExp}" name="cardNumber" size="30"
                       placeholder="${cardNumberInputPlaceholder}" required/></td>
        </tr>
    </table>
    <input type="submit" value="Pay"/>
</form>
<a href="controller?command=gotoclientstartpage">Back to StartPage</a>
<a href="controller?command=clientorders">Back to Client Orders</a>
<a href="controller?command=lookcardamount">Check Card Amount</a>
<a href="controller?command=logout">Logout</a>
</body>
</html>