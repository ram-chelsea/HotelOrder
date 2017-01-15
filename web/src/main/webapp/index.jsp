<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" errorPage="/pages/error/error.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<html>
<head>
    <title>Siqn in</title>
</head>
<body>
<form name="loginForm" method="POST" action="controller">
    <input type="hidden" name="command" value="login" required/>
    <table>
        <tr>
            <td>Login:</td>
            <td><input type="text" name="login" value="" size="20" required/></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type="password" name="password" value="" size="20" required/></td>
        </tr>
    </table>
    ${errorLoginOrPassword} <br/>
    ${errorUserRole} <br/>
    <input type="submit" value="Siqn in"/>
    <a href="controller?command=gotoregistration">Siqn up</a>
</form>
</body>
</html>