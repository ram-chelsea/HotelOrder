<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Clients</title>
</head>
<body>
<h4>${operationMessage}</h4>
<form name="chooseClientsPerPageNumber" method="GET" action="<c:url value="./clients"/>">
    <table>
        <tr>
            <td>Number Per Page:</td>
            <td>
                <select name="clientsPerPage"/>
                <c:forEach var="number" items="${perPageNumbersList}">
                    <option value="<c:out value="${number}"/>"
                            <c:if test="${number == clientsPerPage}">
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
    <form name="chooseCurrentPage" method="GET" action="<c:url value="./clients"/>">
        <input type="hidden" name="clientsPerPage" value="${clientsPerPage}"/>
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
<table border="1">
    <tr bgcolor="#CCCCCC">
        <td align="center"><strong>Name</strong></td>
        <td align="center"><strong>Surname</strong></td>
    </tr>
    <c:forEach var="client" items="${userList}">
        <tr>
            <td><c:out value="${ client.firstName }"/></td>
            <td><c:out value="${ client.lastName }"/></td>
        </tr>
    </c:forEach>
</table>
<a href="<c:url value="../${login}"/>">Back to StartPage</a><br/>
<a href="<c:url value="/login" />">Logout</a>
</body>
</html>