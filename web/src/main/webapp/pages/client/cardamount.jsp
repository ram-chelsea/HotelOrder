<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
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
        <td>${ card.isValid() }</td>
        <td>${ card.amount }</td>
    </tr>
</table>
<a href="controller?command=lookcardamount">Back to Look Other Card Amount</a>
<a href="controller?command=gotoclientstartpage">Back to StartPage</a>
<a href="controller?command=logout">Logout</a>
</body>
</html>
