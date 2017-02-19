<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Rooms</title>
</head>
<body>
<h4>${operationMessage}</h4>
<form name="chooseRoomsPerPageNumber" method="POST" action="controller">
    <input type="hidden" name="command" value="rooms"/>
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
    <input type="submit" value="Choose Number of Clients Per Page"/>
</form>
<c:if test="${numberOfPages > 1}">
    <form name="chooseCurrentPage" method="POST" action="controller">
        <input type="hidden" name="command" value="rooms"/>
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
<form action="controller" method="POST">
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
                                          value="<c:out value="${room.roomId}"/>"/>
                </td>
            </tr>
        </c:forEach>
    </table>
    <br/>
    <input type="hidden" name="command" value="gotochangeroomprice"/>
    <input type="submit" value="Change Room Price"/>
</form>
<a href="controller?command=gotoaddnewroom">Add New Room</a><br/>
<a href="controller?command=gotoadminstartpage">Back to StartPage</a><br/>
<a href="controller?command=logout">Logout</a>
</body>
</html>
