<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<html>
<head>
    <title>Make Order</title>
</head>
<body>
<form name="makeOrderForm" method="POST" action="controller">
    <input type="hidden" name="command" value="makeorder"/>
    <table>
        <tr>
            <td>Roominess:</td>
            <td>
                <select name="roominess"/>
                <c:forEach var="roominess" items="${roominessesList}">
                    <option value="<c:out value="${roominess}"/>">
                        <c:out value="${roominess}"/>
                    </option>
                </c:forEach>
            </td>
        </tr>
        <tr>
            <td>RoomClass:</td>
            <td>
                <select name="roomclass"/>
                <c:forEach var="roomClass" items="${roomsClassesList}">
                    <option value="<c:out value="${roomClass.toString()}"/>">
                        <c:out value="${roomClass.toString()}"/>
                    </option>
                </c:forEach>
            </td>
        </tr>

        <tr>
            <td>From:</td>
            <td><input type="date" name="checkin" min="${minCheckInDate}" value="${minCheckInDate}" size="20" required/></td>
        </tr>
        <tr>
            <td>Till:</td>
            <td><input type="date" name="checkout" value="${minCheckOutDate}" size="20" required/></td>
        </tr>
    </table>
    <input type="submit" value="Search suited rooms"/>
</form>
${operationMessage}<br/>
<a href="controller?command=gotoclientstartpage">Back to StartPage</a><br/>
<a href="controller?command=logout">Logout</a>
</body>
</html>