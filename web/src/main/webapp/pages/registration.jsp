<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" errorPage="/pages/error/error.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<html>
<head>
    <title>Siqn up</title>
</head>
<body>
<form name="registrationForm" method="POST" action="controller">
    <input type="hidden" name="command" value="registration"/>
    <table>
        <tr>
            <td>Name:</td>
            <td><input type="text" name="firstname" value="" size="20"/></td>
        </tr>
        <tr>
            <td>Surname:</td>
            <td><input type="text" name="lastname" value="" size="20"/></td>
        </tr>
        <tr>
            <td>Login:</td>
            <td><input type="text" name="login" value="" size="20"/></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type="password" name="password" value="" size="20"/></td>
        </tr>
    </table>
    <input type="submit" value="Siqn up"/>
    <a href="controller?command=gotologin">Siqn in</a>
</form>
${operationMessage}<br/>
${errorUserExists} <br/>
</body>
</html>