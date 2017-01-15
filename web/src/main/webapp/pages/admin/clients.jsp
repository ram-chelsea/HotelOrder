<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Clients</title>
</head>
<body>
<table border="1">
    <tr bgcolor="#CCCCCC">
        <td align="center"><strong>Name</strong></td>
        <td align="center"><strong>Surname</strong></td>
    </tr>
    <c:forEach var="client" items="${userList}">
        <tr>
            <td><c:out value="${ client.firstName }"/></td>
            <td><c:out value="${ client.lastName }"/></td>
        </tr>
    </c:forEach>
</table>
<a href="controller?command=gotoadminstartpage">Back to StartPage</a>
<a href="controller?command=logout">Logout</a>
</body>
</html>