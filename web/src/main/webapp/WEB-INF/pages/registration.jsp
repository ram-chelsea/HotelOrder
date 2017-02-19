<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>${title}</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js">
    </script>
    <style>

        body {
            font-family: 'Rokkitt', serif;
            font-size: 100%;
            background-color: rgb(56, 214, 112);
            background-repeat: repeat-x;

        }

        .wrapp {
            width: 20%;
            margin: 0 auto;
        }

        .reg-form {
            background: #fff;
            padding: 7%;
            margin-top: 15%;
            position: relative;
            border-radius: 12px;

        }

        @media only screen and (max-width: 1440px) {
            .wrapp {
                width: 30%;
            }

        }

        @media only screen and (max-width: 1366px) {
            .wrapp {
                width: 32%;
            }
        }

        @media only screen and (max-width: 1280px) {
            .wrapp {
                width: 38%;
            }
        }

        @media only screen and (max-width: 1024px) {
            .wrapp {
                width: 47%;
            }

        }

        @media only screen and (max-width: 800px) {
            .wrapp {
                width: 59%;
            }
        }

        @media only screen and (max-width: 640px) {
            .wrapp {
                width: 74%;
            }
        }

        @media only screen and (max-width: 480px) {
            .wrapp {
                width: 62%;
            }
        }

        @media only screen and (max-width: 320px) {
            .wrapp {
                width: 95%;
            }
        }


    </style>
</head>
<body>
<h2>${title}</h2>
<br/>
<a href="<c:url value="../login" />">Go to LoginPage</a>
<div class="wrapp">
    <div class="reg-form">
        <div id="registrationForm">
            <input type="hidden" name="command" value="registration"/>
            <table>
                <tr>
                    <td>Name:</td>
                    <td><input type="text" id="firstname" name="firstname" value="" size="20" placeholder="FirstName"/>
                    </td>
                </tr>
                <tr>
                    <td>Surname:</td>
                    <td><input type="text" id="lastname" name="lastname" value="" size="20" placeholder="LastName"/>
                    </td>
                </tr>
                <tr>
                    <td>Login:</td>
                    <td><input type="text" id="login" name="login" value="" size="20" placeholder="Login"/></td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><input type="password" id="password" name="password" value="" size="20" placeholder="Password"/>
                    </td>
                </tr>
                <tr>
                    <td>Confirm Password:</td>
                    <td><input type="password" id="confirmPassword" name="confirmPassword" value="" size="20"
                               placeholder="Confirm Password"/></td>
                </tr>
                <tr>
                    <td>
                        <input type="hidden" id="userRole" name="userRole" value="CLIENT"/>
                        <button type="button" id="doRegister" onclick="proceed()">Submit</button>
                    </td>
                </tr>
            </table>
        </div>
        <div class="clear"></div>
    </div>

</div>
${operationMessage}<br/>
${userRegistrationMessage} <br/>
<script>

    function proceed() {
        var person = {
            firstName: $("#firstname").val(),
            lastName: $("#lastname").val(),
            login: $("#login").val(),
            password: $("#password").val(),
            userRole: $("#userRole").val()
        };
        $.ajax({
            type: "POST",
            url: '../user/register',
            data: JSON.stringify(person),
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            success: function () {
                alert('The User was registrated');
            }
        });

    }

</script>
</body>
</html>