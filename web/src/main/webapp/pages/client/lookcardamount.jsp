<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Check CardAmount</title>
</head>
<body>
<form name="CheckCardForm" method="GET" action="controller">
    <input type="hidden" name="command" value="cardamount"/>
    <table>
        <tr>
            <td>CardNumber:</td>
            <td><input type="text" pattern="${cardNumberFormatRegExp}" name="cardNumber" size="30"
                       placeholder="${cardNumberInputPlaceholder}" required/></td>
        </tr>
    </table>
    <input type="submit" value="Check"/>
</form>
<a href="controller?command=gotoclientstartpage">Back to StartPage</a>
<a href="controller?command=logout">Logout</a>
</body>
</html>
