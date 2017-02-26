<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         errorPage="/pages/error/error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<html>
<head>
    <title>Add New Room</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js">
    </script>
</head>
<body>
${formSettingsError}<br/>
<div class="wrapp">
    <div class="reg-form">
        <div id="addingRoomForm">
            <table>
                <tr>
                    <td>RoomNumber:</td>
                    <td><input type="text" pattern="${newRoomNumberFormatRegExp}" name="roomNumber" id="roomNumber"
                               size="30"
                               placeholder="${newRoomNumberInputPlaceholder}" required/></td>
                </tr>

                <tr>
                    <td>Roominess:</td>
                    <td><input type="number" name="roominess" id="roominess" min="${newRoomMinRoominess}"
                               step="${newRoomRoominessStep}"
                               required/></td>
                </tr>

                <tr>
                    <td>RoomClass:</td>
                    <td>
                        <select name="roomClass" id="roomClass"/>
                        <c:forEach var="roomclass" items="${roomsClassesList}">
                            <option value="<c:out value="${roomclass.toString()}"/>">
                                <c:out value="${roomclass.toString()}"/>
                            </option>
                        </c:forEach>
                    </td>
                </tr>

                <tr>
                    <td>Price:</td>
                    <td><input type="number" name="roomPrice" id="roomPrice" min="${newRoomMinPrice}"
                               step="${newRoomPriceStep}" required/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <button type="button" id="addRoom" onclick="proceed()">Submit</button>
                    </td>
                </tr>
            </table>
        </div>
        <div class="clear"></div>
    </div>
</div>
${operationMessage} <br/>
<a href="<c:url value="../rooms"/>">Back to RoomsList</a><br/>
<a href="<c:url value="/admins/${login}"/>">Back to StartPage</a><br/>
<a href="<c:url value="/login" />">Logout</a>
<script>
    function proceed() {
        var room = {
            roomNumber: $("#roomNumber").val(),
            roominess: $("#roominess").val(),
            roomClass: $("#roomClass").val(),
            roomPrice: $("#roomPrice").val()
        };
        $.ajax({
            type: "POST",
            url: '../rooms/addnewroom',
            data: JSON.stringify(room),
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            success: function () {
                alert('The Room was Added');
            }
        });
    }
</script>
</body>
</html>