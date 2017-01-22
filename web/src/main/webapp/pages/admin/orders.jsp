<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Orders</title>
</head>
<body>
<h4>${operationMessage}</h4>

<h3>Requested Orders</h3>
<table border="1">
    <tr bgcolor="#CCCCCC">
        <td align="center"><strong>OrderId</strong></td>
        <td align="center"><strong>RoomNumber</strong></td>
        <td align="center"><strong>Client</strong></td>
        <td align="center"><strong>ClientId</strong></td>
        <td align="center"><strong>CheckInDate</strong></td>
        <td align="center"><strong>CheckOutDate</strong></td>
        <td align="center"><strong>TotalPrice</strong></td>
        <td align="center"><strong>Confirm</strong></td>
        <td align="center"><strong>Deny</strong></td>
    </tr>
    <c:forEach var="order" items="${requestedOrdersList}">
        <form action="controller" method="POST">
            <input type="hidden" name="orderId" value="<c:out value="${order.orderId }"/>"/>
            <tr>
                <td><c:out value="${ order.orderId }"/></td>
                <td><c:out value="${ order.room.roomNumber }"/></td>
                <td><c:out value="${ order.user.firstName }  ${ order.user.lastName }"/></td>
                <td><c:out value="${ order.user.userId }"/></td>
                <td><c:out value="${ order.checkInDate }"/></td>
                <td><c:out value="${ order.checkOutDate }"/></td>
                <td><c:out value="${order.totalPrice}"/></td>
                <td><input type="submit" name="command" value="confirm"/></td>
                <td><input type="submit" name="command" value="deny"/></td>
            </tr>
        </form>
    </c:forEach>
</table>
<h3>Cancelled Orders</h3>
<table border="1">
    <tr bgcolor="#CCCCCC">
        <td align="center"><strong>OrderId</strong></td>
        <td align="center"><strong>RoomNumber</strong></td>
        <td align="center"><strong>Client</strong></td>
        <td align="center"><strong>ClientId</strong></td>
        <td align="center"><strong>CheckInDate</strong></td>
        <td align="center"><strong>CheckOutDate</strong></td>
        <td align="center"><strong>TotalPrice</strong></td>
    </tr>
    <c:forEach var="order" items="${cancelledOrdersList}">
        <tr>
            <td><c:out value="${ order.orderId }"/></td>
            <td><c:out value="${ order.room.roomNumber }"/></td>
            <td><c:out value="${ order.user.firstName }  ${ order.user.lastName }"/></td>
            <td><c:out value="${ order.user.userId }"/></td>
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
        <td align="center"><strong>Client</strong></td>
        <td align="center"><strong>ClientId</strong></td>
        <td align="center"><strong>CheckInDate</strong></td>
        <td align="center"><strong>CheckOutDate</strong></td>
        <td align="center"><strong>TotalPrice</strong></td>
        <td align="center"><strong>Complete</strong></td>
    </tr>
    <c:forEach var="order" items="${allPaidOrdersList}">
        <form action="controller" method="POST">
            <input type="hidden" name="orderId" value="<c:out value="${order.orderId}"/>"/>
            <input type="hidden" name="command" value="complete"/>
            <tr>
                <td><c:out value="${ order.orderId }"/></td>
                <td><c:out value="${ order.room.roomNumber }"/></td>
                <td><c:out value="${ order.user.firstName }  ${ order.user.lastName }"/></td>
                <td><c:out value="${ order.user.userId }"/></td>
                <td><c:out value="${ order.checkInDate }"/></td>
                <td><c:out value="${ order.checkOutDate }"/></td>
                <td><c:out value="${ order.totalPrice}"/></td>
                <td><input type="submit" value="Complete Order"/></td>
            </tr>
        </form>
    </c:forEach>
</table>
<h3>Confirmed Orders</h3>
<table border="1">
    <tr bgcolor="#CCCCCC">
        <td align="center"><strong>OrderId</strong></td>
        <td align="center"><strong>RoomNumber</strong></td>
        <td align="center"><strong>Client</strong></td>
        <td align="center"><strong>ClientId</strong></td>
        <td align="center"><strong>CheckInDate</strong></td>
        <td align="center"><strong>CheckOutDate</strong></td>
        <td align="center"><strong>TotalPrice</strong></td>
    </tr>
    <c:forEach var="order" items="${confirmedOrdersList}">
        <tr>
            <td><c:out value="${ order.orderId }"/></td>
            <td><c:out value="${ order.room.roomNumber }"/></td>
            <td><c:out value="${ order.user.firstName }  ${ order.user.lastName }"/></td>
            <td><c:out value="${ order.user.userId }"/></td>
            <td><c:out value="${ order.checkInDate }"/></td>
            <td><c:out value="${ order.checkOutDate }"/></td>
            <td><c:out value="${order.totalPrice}"/></td>
        </tr>
    </c:forEach>
</table>
<h3>Completed Orders</h3>
<table border="1">
    <tr bgcolor="#CCCCCC">
        <td align="center"><strong>OrderId</strong></td>
        <td align="center"><strong>RoomNumber</strong></td>
        <td align="center"><strong>Client</strong></td>
        <td align="center"><strong>ClientId</strong></td>
        <td align="center"><strong>CheckInDate</strong></td>
        <td align="center"><strong>CheckOutDate</strong></td>
        <td align="center"><strong>TotalPrice</strong></td>
    </tr>
    <c:forEach var="order" items="${completedOrdersList}">
        <tr>
            <td><c:out value="${ order.orderId }"/></td>
            <td><c:out value="${ order.room.roomNumber }"/></td>
            <td><c:out value="${ order.user.firstName }  ${ order.user.lastName }"/></td>
            <td><c:out value="${ order.user.userId }"/></td>
            <td><c:out value="${ order.checkInDate }"/></td>
            <td><c:out value="${ order.checkOutDate }"/></td>
            <td><c:out value="${order.totalPrice}"/></td>
        </tr>
    </c:forEach>
</table>
<a href="controller?command=gotoadminstartpage">Back to StartPage</a>
<a href="controller?command=logout">Logout</a>
</body>
</html>
