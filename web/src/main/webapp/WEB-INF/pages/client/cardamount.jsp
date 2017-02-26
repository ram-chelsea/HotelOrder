<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>CardAmount</title>
</head>
<body>
<table border="1">
    <tr bgcolor="#CCCCCC">
        <td align="center"><strong>CardNumber:</strong></td>
        <td align="center"><strong>Validity</strong></td>
        <td align="center"><strong>amount</strong></td>
    </tr>

    <tr>
        <td>${ card.cardNumber }</td>
        <td>${ card.isValid }</td>
        <td>${ card.amount }</td>
    </tr>
</table>
${operationMessage}<br/>
<a href="<c:url value="/clients/${login}/creditcards/checkcard"/>">Back to Look Other Card Amount</a><br/>
<a href="<c:url value="/clients/${login}"/>">Back to StartPage</a><br/>
<a href="<c:url value="/login" />">Logout</a>
</body>
</html>
