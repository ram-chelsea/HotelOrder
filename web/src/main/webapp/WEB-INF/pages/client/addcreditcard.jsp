<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" errorPage="/pages/error/error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<html>
<head>
    <title>Add Credit Card</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js">
    </script>
</head>
<body>
${formSettingsError}
<form name="newCreditCardForm">
    <table>
        <tr>
            <td>CardNumber:</td>
            <td><input type="text" pattern="${cardNumberFormatRegExp}" name="cardNumber" id="cardNumber" size="30"
                       placeholder="${cardNumberInputPlaceholder}" required/></td>
        </tr>

        <tr>
            <td>Amount:</td>
            <td><input type="number" name="amount" id="amount" min="${newCardMinAmount}" step="${newCardAmountStep}"
                       placeholder="${amountInputPlaceHolder}" required/></td>
        </tr>
        <tr>
            <td>
                <input type="hidden" id="isValid" name="isValid" value="true"/>
                <button type="button" id="doAddingCard" onclick="proceed()">Submit</button>
            </td>
        </tr>
    </table>
</form>
${operationMessage}<br/>
<a href="<c:url value="/clients/${login}"/>">Back to StartPage</a><br/>
<a href="<c:url value="./checkcard"/>">Check Credit Card Amount</a> <br/>
<a href="<c:url value="/login" />">Logout</a>
<script>

    function proceed() {
        var card = {
            cardNumber: $("#cardNumber").val(),
            amount: $("#amount").val(),
            isValid:  $("#isValid").val()
        };
        $.ajax({
            type: "POST",
            url: '../creditcards/addcard',
            data: JSON.stringify(card),
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            success: function () {
                alert('The Card was added');
            }
        });
    }
</script>
</body>
</html>
