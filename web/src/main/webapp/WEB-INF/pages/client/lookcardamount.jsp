<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Check CardAmount</title>
</head>
<body>
<form name="CheckCardForm" method="POST" action="<c:url value="../creditcards/checkcard"/>">
    <table>
        <tr>
            <td>CardNumber:</td>
            <td><input type="text" pattern="${cardNumberFormatRegExp}" name="cardNumber" size="30"
                       placeholder="${cardNumberInputPlaceholder}" required/></td>
        </tr>
    </table>
    <input type="submit" value="Check"/>
</form>
${operationMessage}<br/>
<a href="<c:url value="/clients/${login}"/>">Back to StartPage</a><br/>
<a href="<c:url value="/login" />">Logout</a>
</body>
</html>
