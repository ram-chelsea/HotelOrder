<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>Welcome, Client</title>
</head>
<body>
<h2>${user.firstName} ${user.lastName}</h2>
<h2>${user.login}</h2>
<a href="controller?command=clientorders">Show OrdersList</a> <br/>
<a href="controller?command=gotomakeorder">Make Order</a> <br/>
<a href="controller?command=lookcardamount">Check Credit Card Amount</a> <br/>
<a href="controller?command=gotoaddcreditcard">Add Credit Card</a> <br/>
<a href="controller?command=logout">Logout</a> <br/>
</body>
</html>
