<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Client Orders</title>
</head>
<body>
<h4>${operationMessage}</h4>
<h3>Confirmed Orders</h3>
<table border="1">
    <tr bgcolor="#CCCCCC">
        <td align="center"><strong>OrderId</strong></td>
        <td align="center"><strong>RoomNumber</strong></td>
        <td align="center"><strong>CheckInDate</strong></td>
        <td align="center"><strong>CheckOutDate</strong></td>
        <td align="center"><strong>TotalPrice</strong></td>
        <td align="center"><strong>Pay</strong></td>
        <td align="center"><strong>Cancel</strong></td>
    </tr>
    <c:forEach var="order" items="${confirmedClientOrdersList}">
        <form action="controller" method="POST">
            <input type="hidden" name="orderId" value="<c:out value="${order.orderId}"/>"/>
            <input type="hidden" name="command" value="gotopaycancel"/>
            <tr>
                <td><c:out value="${ order.orderId }"/></td>
                <td><c:out value="${ order.room.roomNumber }"/></td>
                <td><c:out value="${ order.checkInDate }"/></td>
                <td><c:out value="${ order.checkOutDate }"/></td>
                <td><c:out value="${ order.totalPrice}"/></td>
                <td><input type="submit" name="gotopaycancelbutton" value="gotopay"/></td>
                <td><input type="submit" name="gotopaycancelbutton" value="cancel"/></td>
            </tr>
        </form>
    </c:forEach>
</table>
<h3>Requested Orders</h3>
<table border="1">
    <tr bgcolor="#CCCCCC">
        <td align="center"><strong>OrderId</strong></td>
        <td align="center"><strong>RoomNumber</strong></td>
        <td align="center"><strong>CheckInDate</strong></td>
        <td align="center"><strong>CheckOutDate</strong></td>
        <td align="center"><strong>TotalPrice</strong></td>
    </tr>
    <c:forEach var="order" items="${requestedClientOrdersList}">
        <tr>
            <td><c:out value="${ order.orderId }"/></td>
            <td><c:out value="${ order.room.roomNumber }"/></td>
            <td><c:out value="${ order.checkInDate }"/></td>
            <td><c:out value="${ order.checkOutDate }"/></td>
            <td><c:out value="${ order.totalPrice}"/></td>
        </tr>
    </c:forEach>
</table>
<h3>Denied Orders</h3>
<table border="1">
    <tr bgcolor="#CCCCCC">
        <td align="center"><strong>OrderId</strong></td>
        <td align="center"><strong>RoomNumber</strong></td>
        <td align="center"><strong>CheckInDate</strong></td>
        <td align="center"><strong>CheckOutDate</strong></td>
        <td align="center"><strong>TotalPrice</strong></td>
    </tr>
    <c:forEach var="order" items="${deniedClientOrdersList}">
        <tr>
            <td><c:out value="${ order.orderId }"/></td>
            <td><c:out value="${ order.room.roomNumber }"/></td>
            <td><c:out value="${ order.checkInDate }"/></td>
            <td><c:out value="${ order.checkOutDate }"/></td>
            <td><c:out value="${ order.totalPrice}"/></td>
        </tr>
    </c:forEach>
</table>
<h3>Paid Orders</h3>
<table border="1">
    <tr bgcolor="#CCCCCC">
        <td align="center"><strong>OrderId</strong></td>
        <td align="center"><strong>RoomNumber</strong></td>
        <td align="center"><strong>CheckInDate</strong></td>
        <td align="center"><strong>CheckOutDate</strong></td>
        <td align="center"><strong>TotalPrice</strong></td>
    </tr>
    <c:forEach var="order" items="${paidClientOrdersList}">
        <tr>
            <td><c:out value="${ order.orderId }"/></td>
            <td><c:out value="${ order.room.roomNumber }"/></td>
            <td><c:out value="${ order.checkInDate }"/></td>
            <td><c:out value="${ order.checkOutDate }"/></td>
            <td><c:out value="${ order.totalPrice}"/></td>
        </tr>
    </c:forEach>
</table>
<h3>Completed Orders</h3>
<table border="1">
    <tr bgcolor="#CCCCCC">
        <td align="center"><strong>OrderId</strong></td>
        <td align="center"><strong>RoomNumber</strong></td>
        <td align="center"><strong>CheckInDate</strong></td>
        <td align="center"><strong>CheckOutDate</strong></td>
        <td align="center"><strong>TotalPrice</strong></td>
    </tr>
    <c:forEach var="order" items="${completedClientOrdersList}">
        <tr>
            <td><c:out value="${ order.orderId }"/></td>
            <td><c:out value="${ order.room.roomNumber }"/></td>
            <td><c:out value="${ order.checkInDate }"/></td>
            <td><c:out value="${ order.checkOutDate }"/></td>
            <td><c:out value="${ order.totalPrice}"/></td>
        </tr>
    </c:forEach>
</table>
<a href="controller?command=gotoclientstartpage">Back to StartPage</a>
<a href="controller?command=logout">Logout</a>
</body>
</html>
