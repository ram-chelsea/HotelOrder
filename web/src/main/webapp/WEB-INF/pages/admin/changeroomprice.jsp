<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Change RoomPrice</title>
</head>
<body>
${formSettingsError}<br/>
<table border="1">
    <tr bgcolor="#CCCCCC">
        <td align="center"><strong>RoomNumber</strong></td>
        <td align="center"><strong>Roominess</strong></td>
        <td align="center"><strong>RoomClass</strong></td>
        <td align="center"><strong>PriceForNight</strong></td>
    </tr>
    <tr>
        <td>${ room.roomNumber }</td>
        <td>${ room.roominess }</td>
        <td>${ room.roomClass }</td>
        <td>${ room.price }</td>
    </tr>
</table>
<form action="controller" method="POST">
    <input type="hidden" name="command" value="changeroomprice"/>
    <input type="hidden" name="roomId" value="${room.roomId}"/>
    <input type="number" name="newprice" min="${roomMinNewPrice}" step="${roomNewPriceStep}"
           placeholder="${roomNewPriceInputPlaceHolder}" required/><br>
    <input type="submit" value="Change RoomPrice"/>
</form>
${operationMessage}
<a href="controller?command=gotoadminstartpage">Back to StartPage</a><br/>
<a href="controller?command=rooms">Back to RoomsList</a><br/>
<a href="controller?command=logout">Logout</a>
</body>
</html>
