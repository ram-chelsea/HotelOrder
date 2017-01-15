<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Rooms</title>
</head>
<body>
<table border="1">
    <tr bgcolor="#CCCCCC">
        <td align="center"><strong>RoomNumber</strong></td>
        <td align="center"><strong>Roominess</strong></td>
        <td align="center"><strong>RoomClass</strong></td>
        <td align="center"><strong>PriceForNight</strong></td>
        <td align="center"><strong>ChangePrice</strong></td>
    </tr>
    <c:forEach var="room" items="${roomsList}">
        <form action="controller" method="POST">
            <input type="hidden" name="roomId" value="<c:out value="${room.roomId}"/>"/>
            <input type="hidden" name="command" value="gotochangeroomprice"/>
            <tr>
                <td><c:out value="${ room.roomNumber }"/></td>
                <td><c:out value="${ room.roominess }"/></td>
                <td><c:out value="${ room.roomClass }"/></td>
                <td><c:out value="${ room.price }"/></td>
                <td><input type="submit" value="Change RoomPrice"/></td>
            </tr>
        </form>
    </c:forEach>
</table>
<a href="controller?command=gotoaddnewroom">Add New Room</a>
<a href="controller?command=gotoadminstartpage">Back to StartPage</a>
<a href="controller?command=logout">Logout</a>
</body>
</html>
