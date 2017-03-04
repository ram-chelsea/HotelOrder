<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Rooms</title>
</head>
<body>
<form name="chooseRoomsPerPageNumber" method="GET" action="<c:url value="./rooms"/>">
    <table>
        <tr>
            <td>Number Per Page:</td>
            <td>
                <select name="roomsPerPage"/>
                <c:forEach var="number" items="${perPageNumbersList}">
                    <option value="<c:out value="${number}"/>"
                            <c:if test="${number == roomsPerPage}">
                                selected="selected"
                            </c:if>>
                        <c:out value="${number}"/>
                    </option>
                </c:forEach>
            </td>
        </tr>
    </table>
    <input type="submit" value="Choose Number of Rooms Per Page"/>
</form>
<c:if test="${numberOfPages > 1}">
    <form name="chooseCurrentPage" method="GET" action="<c:url value="./rooms"/>">
        <input type="hidden" name="roomsPerPage" value="${roomsPerPage}"/>
        <table>
            <tr>
                <td>CurrentPage:</td>
                <td>
                    <select name="currentPage"/>
                    <c:forEach var="page" begin="1" end="${numberOfPages}">
                        <option value="<c:out value="${page}"/>"
                                <c:if test="${page == currentPage}">
                                    selected="selected"
                                </c:if>>
                            <c:out value="${page}"/>
                        </option>
                    </c:forEach>
                </td>
            </tr>
        </table>
        <input type="submit" value="Show Page Number"/>
    </form>
</c:if>
<c:choose>
    <c:when test="${!roomsList.isEmpty()}">
        <form action="<c:url value="./rooms/roomprice"/>" method="GET">
            <table border="1">
                <tr bgcolor="#CCCCCC">
                    <td align="center"><strong>RoomNumber</strong></td>
                    <td align="center"><strong>Roominess</strong></td>
                    <td align="center"><strong>RoomClass</strong></td>
                    <td align="center"><strong>PriceForNight</strong></td>
                    <td align="center"><strong>ChangePrice</strong></td>
                </tr>
                <c:forEach var="room" items="${roomsList}">
                    <tr>
                        <td><c:out value="${ room.roomNumber }"/></td>
                        <td><c:out value="${ room.roominess }"/></td>
                        <td><c:out value="${ room.roomClass }"/></td>
                        <td><c:out value="${ room.price }"/></td>
                        <td align="center"><input type="radio" name="roomId"
                                                  value="<c:out value="${room.roomId}"/>"
                                <c:if test="${room == roomsList.get(0)}">
                                    checked="checked"
                                </c:if>
                        />
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <br/>
            <input type="submit" value="Change Room Price"/>
        </form>
    </c:when>
    <c:otherwise>
        <h3>There are not any rooms</h3>
    </c:otherwise>
</c:choose>
${operationMessage}<br/>
<a href="<c:url value="./rooms/newroom"/>">Add New Room</a><br/>
<a href="<c:url value="/admin"/>">Back to StartPage</a><br/>
<a href="<c:url value="/login" />">Logout</a>
</body>
</html>
