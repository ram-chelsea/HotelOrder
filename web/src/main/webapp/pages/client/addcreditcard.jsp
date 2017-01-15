<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" errorPage="/pages/error/error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<html>
<head>
    <title>Add Credit Card</title>
</head>
<body>
${formSettingsError}
<form name="newCreditCardForm" method="POST" action="controller">
    <input type="hidden" name="command" value="addcreditcard"/>
    <table>
        <tr>
            <td>CardNumber:</td>
            <td><input type="text" pattern="${cardNumberFormatRegExp}" name="cardNumber" size="30"
                       placeholder="${cardNumberInputPlaceholder}" required/></td>
        </tr>

        <tr>
            <td>Amount:</td>
            <td><input type="number" name="amount" min="${newCardMinAmount}" step="${newCardAmountStep}"
                       placeholder="${amountInputPlaceHolder}" required/></td>
        </tr>

    </table>
    <input type="submit" value="Add Credit Card"/>
</form>
${operationMessage}<br/>
<a href="controller?command=gotoclientstartpage">Back to StartPage</a>
<a href="controller?command=lookcardamount">Check Credit Card Amount</a> <br/>
<a href="controller?command=logout">Logout</a> <br/>
</body>
</html>
