<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Request Room</title>
</head>
<body>
<table border="1">
    <tr bgcolor="#CCCCCC">
        <td align="center"><strong>RoomNumber</strong></td>
        <td align="center"><strong>Roominess</strong></td>
        <td align="center"><strong>RoomClass</strong></td>
        <td align="center"><strong>Price</strong></td>
        <td align="center"><strong>Make Request</strong></td>
    </tr>
    <c:forEach var="room" items="${suitedRoomsList}">
        <form action="<c:url value="../orders/requestroom"/>" method="POST">
            <input type="hidden" name="roomId" value="<c:out value="${room.roomId}"/>"/>
            <input type="hidden" name="orderRoomForm" value="<c:out value="${orderRoomForm}"/>"/>
            <tr>
                <td><c:out value="${ room.roomNumber }"/></td>
                <td><c:out value="${ room.roominess }"/></td>
                <td><c:out value="${ room.roomClass }"/></td>
                <td><c:out value="${ room.price }"/></td>
                <td><input type="submit" value="make Request"/></td>
            </tr>
        </form>
    </c:forEach>
</table>
<a href="<c:url value="/clients/${login}/orders/makeorder"/>">Back to Make a New Order</a><br/>
<a href="<c:url value="/clients/${login}"/>">Back to StartPage</a><br/>
<a href="<c:url value="/login" />">Logout</a>
</body>
</html>
