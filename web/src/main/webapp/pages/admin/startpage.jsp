<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>Welcome, Admin</title>
</head>
<body>
<h2>${user.firstName} ${user.lastName}</h2>
<h2>${user.login}</h2>
<a href="controller?command=clients">Show Clients List</a> <br/>
<a href="controller?command=adminorders">Show Orders List</a> <br/>
<a href="controller?command=rooms">Show Rooms List</a> <br/>
<a href="controller?command=logout">Logout</a>
</body>
</html>