<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Client Orders</title>
</head>
<body>
<form name="chooseOrderStatus" method="GET" action="<c:url value="./orders"/>">
    <table>
        <tr>
            <td>Order Status:</td>
            <td>
                <select name="orderStatus"/>
                <c:forEach var="status" items="${orderStatusesList}">
                    <option value="<c:out value="${status.toString()}"/>"
                            <c:if test="${status == orderStatus}">
                                selected="selected"
                            </c:if>>
                        <c:out value="${status.toString()}"/>
                    </option>
                </c:forEach>
            </td>
        </tr>
    </table>
    <input type="submit" value="Show Orders with chosen Status"/>
</form>

<c:choose>
    <c:when test="${!ordersList.isEmpty()}">
        <h4>${orderStatus.toString()} ORDERS</h4>
        <table border="1">
            <tr bgcolor="#CCCCCC">
                <td align="center"><strong>OrderId</strong></td>
                <td align="center"><strong>RoomNumber</strong></td>
                <td align="center"><strong>CheckInDate</strong></td>
                <td align="center"><strong>CheckOutDate</strong></td>
                <td align="center"><strong>TotalPrice</strong></td>
                <c:choose>
                    <c:when test="${orderStatus.toString().equals('CONFIRMED')}">
                        <td align="center"><strong>Pay</strong></td>
                        <td align="center"><strong>Cancel</strong></td>
                    </c:when>
                </c:choose>
            </tr>
            <c:forEach var="order" items="${ordersList}">

                <input type="hidden" name="orderId" value="<c:out value="${order.orderId }"/>"/>
                <tr>
                    <td><c:out value="${ order.orderId }"/></td>
                    <td><c:out value="${ order.room.roomNumber }"/></td>
                    <td><c:out value="${ order.checkInDate }"/></td>
                    <td><c:out value="${ order.checkOutDate }"/></td>
                    <td><c:out value="${ order.totalPrice}"/></td>
                    <c:choose>
                        <c:when test="${orderStatus.toString().equals('CONFIRMED')}">
                            <form action="<c:url value="./orders/gotopay"/>" method="POST">
                                <input type="hidden" name="orderId" value="<c:out value="${order.orderId }"/>"/>
                                <td><input type="submit" name="goToPay" value="gotopay"/></td>
                            </form>
                            <form action="<c:url value="./orders/changestatus"/>" method="POST">
                                <input type="hidden" name="orderId" value="<c:out value="${order.orderId }"/>"/>
                                <td><input type="submit" name="newStatus" value="cancel"/></td>
                            </form>
                        </c:when>
                    </c:choose>
                </tr>
            </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
        <h3>There are not ${orderStatus.toString()} orders now</h3>
    </c:otherwise>
</c:choose>
<h4>${operationMessage}</h4><br/>
<a href="<c:url value="/client"/>">Back to StartPage</a><br/>
<a href="<c:url value="/login" />">Logout</a>
</body>
</html>
