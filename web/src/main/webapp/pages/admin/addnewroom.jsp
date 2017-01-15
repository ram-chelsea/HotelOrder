<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         errorPage="/pages/error/error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<html>
<head>
    <title>Add New Room</title>
</head>
<body>
${formSettingsError}<br/>
<form name="newRoomForm" method="POST" action="controller">
    <input type="hidden" name="command" value="addnewroom"/>
    <table>
        <tr>
            <td>RoomNumber:</td>
            <td><input type="text" pattern="${newRoomNumberFormatRegExp}" name="roomNumber" size="30"
                       placeholder="${newRoomNumberInputPlaceholder}" required/></td>
        </tr>

        <tr>
            <td>Roominess:</td>
            <td><input type="number" name="roominess" min="${newRoomMinRoominess}" step="${newRoomRoominessStep}"
                       required/></td>
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
            <td>Price:</td>
            <td><input type="number" name="roomPrice" min="${newRoomMinPrice}" step="${newRoomPriceStep}" required/>
            </td>
        </tr>

    </table>
    <input type="submit" value="Add Room"/>
</form>
${operationMessage} <br/>
<a href="controller?command=gotoadminstartpage">Back to StartPage</a>
<a href="controller?command=rooms">Go to Rooms List</a> <br/>
<a href="controller?command=logout">Logout</a> <br/>
</body>
</html>