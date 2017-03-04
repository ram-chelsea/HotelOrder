<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>${title}</title>
    <link href='http://fonts.googleapis.com/css?family=Rokkitt' rel='stylesheet' type='text/css'>
    <style>

        /* normalize */
        html, body, div, span, applet, object, iframe, h1, h2, h3, h4, h5, h6, p, blockquote, pre, a, abbr, acronym, address, big, cite, code, del, dfn, em, img, ins, kbd, q, s, samp, small, strike, strong, sub, sup, tt, var, b, u, i, dl, dt, dd, ol, nav ul, nav li, fieldset, form, label, legend, table, caption, tbody, tfoot, thead, tr, th, td, article, aside, canvas, details, embed, figure, figcaption, footer, header, hgroup, menu, nav, output, ruby, section, summary, time, mark, audio, video {
            margin: 0;
            padding: 0;
            border: 0;
            font-size: 100%;
            font: inherit;
            vertical-align: baseline;
        }

        article, aside, details, figcaption, figure, footer, header, menu, nav, section {
            display: block;
        }

        ol, ul {
            list-style: none;
            margin: 0;
            padding: 0;
        }

        blockquote, q {
            quotes: none;
        }

        blockquote:before, blockquote:after, q:before, q:after {
            content: '';
            content: none;
        }

        table {
            border-collapse: collapse;
            border-spacing: 0;
        }

        /* start editing from here */
        a {
            text-decoration: none;
        }

        .clear {
            clear: both;
        }

        /* clear float */
        nav.vertical ul li {
            display: block;
        }

        /* vertical menu */
        nav.horizontal ul li {
            display: inline-block;
        }

        /* horizontal menu */
        img {
            max-width: 100%;
        }

        /*end normalize*/
        body {
            font-family: 'Rokkitt', serif;
            font-size: 100%;
            background-color: rgb(13, 151, 214);
            background-repeat: repeat-x;
            background: -webkit-gradient(linear, 0% 0%, 0% 100%, from(rgb(13, 151, 214)), to(#2F2727));
            background: -webkit-linear-gradient(top, #2F2727, rgb(13, 151, 214));
            background: -moz-linear-gradient(top, #2F2727, rgb(13, 151, 214));
            background: -ms-linear-gradient(top, #2F2727, rgb(13, 151, 214));
            background: -o-linear-gradient(top, #2F2727, rgb(13, 151, 214));
        }

        .wrap {
            width: 50%;
            margin: 0 auto;
        }

        /*start-login-form*/
        .login_form {
            width: 55%;
        / / background: url("../images/border.png") no-repeat 331 px 77 px;
            float: left;
            position: relative;
        }

        .login-form {
            background: #fff;
            padding: 7%;
            margin-top: 15%;
            position: relative;
            border-radius: 12px;
            -webkit-border-radius: 12px;
            -moz-border-radius: 12px;
            -o-border-radius: 12px;
        }

        .login-form h1 {
            font-size: 1.7em;
            color: #474646;
        }

        .login_form ul {
            list-style-type: none;
            list-style-position: outside;
            margin: 0px;
            padding: 0px;
        }

        .login_form li {
            position: relative;
        }

        /* form element visual styles */
        .login_form li {
            float: left;
            outline: none;
            border: 1px solid #DDDDDD;
            font-size: 1.2em;
            color: #B6B6B6;
            width: 78.5%;
            font-weight: 600;
            margin-top: 8%;
            position: relative;
            height: 42px;
            border-radius: 2px;
            -webkit-border-radius: 2px;
            -moz-border-radius: 2px;
            -o-border-radius: 2px;
            transition: all 0.5s ease-out;
            -webkit-transition: all 0.5s ease-out;
            -moz-transition: all 0.5s ease-out;
            -ms-transition: all 0.5s ease-out;
            -o-transition: all 0.5s ease-out;
        }

        .login_form li:hover {
            border: 1px solid #79B42B;
            color: #79B42B;
        }

        .login_form input {
            float: left;
            font-size: 1.1em;
            font-family: 'Rokkitt', serif;
            padding: 7px 14px;
            width: 75%;
            border: none;
            outline: none;
            color: #B6B6B6;
        }

        .login_form p {
            float: right;
            padding: 8px 7px;
            width: 9.3%;
            cursor: pointer;
        }

        .login_form input[type="submit"] {
            border: none;
            outline: none;
            cursor: pointer;
            color: #fff;
            background: #79B42B;
            width: 79%;
            padding: 12px;
            font-size: 1.3em;
            letter-spacing: 1px;
            margin: 28px 0 30px;
            transition: all 0.5s ease-out;
            -webkit-transition: all 0.5s ease-out;
            -moz-transition: all 0.5s ease-out;
            -ms-transition: all 0.5s ease-out;
            -o-transition: all 0.5s ease-out;
            border-radius: 2px;
            -webkit-border-radius: 2px;
            -moz-border-radius: 2px;
            -o-border-radius: 2px;
        }

        .login_form input[type="submit"]:hover {
            background: #88C470;
        }

        .login_form input[type="checkbox"] {
            width: 21px;
            vertical-align: middle;
            padding: 5px;
            float: left;
        }

        .login_form i {
            font-size: 1.2em;
            color: #B6B6B6;
            width: 40%;
            float: left;
        }

        .forgot a {
            color: #B6B6B6;
            font-size: 1.1em;
            -webkit-transition: all 0.5s ease-out;
            -moz-transition: all 0.5s ease-out;
            -ms-transition: all 0.5s ease-out;
            -o-transition: all 0.5s ease-out;
        }

        .forgot a:hover {
            color: #79B42B;
        }

        /* === Form hints === */

        .login_form input:focus + .form_hint {
            display: inline;
        }

        .login_form input:required:valid + .form_hint {
            color: #000;
            background: #79B42B;
        }

        .login_form input:required:valid + .form_hint::before {
            color: #28921f;
        }

        /*end-login-form*/
        /*start-account*/
        .account {
            float: left;
            width: 45%;
        }

        .account h2 a {
            color: #71B8E4;
            display: block;
            font-size: 1.3em;
            font-weight: 400;
            text-align: right;
            margin-top: 3px;
            transition: all 0.5s ease-out;
            -webkit-transition: all 0.5s ease-out;
            -moz-transition: all 0.5s ease-out;
            -ms-transition: all 0.5s ease-out;
            -o-transition: all 0.5s ease-out;
        }

        .account h2 a:hover {
            color: #79B42B;
        }

        .account button {
            background: transparent;
            border: 0;
            padding: 3px 6px;
            font-family: inherit;
            font-size: inherit;
            cursor: pointer;
        }

        /*end-checkbox*/
        /*-----start-responsive-design------*/
        @media only screen and (max-width: 1440px) {
            .wrap {
                width: 56%;
            }

            .login_form p {
                padding: 8px 7px;
            }
        }

        @media only screen and (max-width: 1366px) {
            .wrap {
                width: 59%;
            }

            .login_form p {
                padding: 8px 3px;
            }
        }

        @media only screen and (max-width: 1280px) {
            .wrap {
                width: 63%;
            }
        }

        @media only screen and (max-width: 1024px) {
            .wrap {
                width: 79%;
            }

            .login_form input {
                width: 77%;
            }

        }

        @media only screen and (max-width: 800px) {
            .wrap {
                width: 95%;
            }

            .login_form {
            / / background: url("../images/border.png") no-repeat 313 px 77 px;
            }
        }

        @media only screen and (max-width: 640px) {
            .wrap {
                width: 95%;
            }

            .login-form h1 {
                font-size: 1.3em;
            }

            .login_form li {
                height: 37px;
                margin-top: 7.3%;
            }

            .login_form input {
                font-size: 1em;
                padding: 5px 14px;
            }

            .login_form input[type="submit"] {
                font-size: 1.2em;
                padding: 10px;
                margin: 22px 0 30px;
            }

            .account h2 a {
                font-size: 0.9em;
            }

        }

        @media only screen and (max-width: 480px) {
            .wrap {
                width: 56%;
            }

            .login_form {
                width: 100%;
                background: none;
            }

            .login_form li {
                width: 99.5%;
            }

            .login_form input[type="submit"] {
                width: 100%;
                margin: 22px 0 13px;
            }

            .account {
                width: 100%;
            }

            .account h2 a {
                text-align: left;
                margin-top: 15px;
            }

            .footer p {
                font-size: 1.1em;
            }
        }

        @media only screen and (max-width: 320px) {
            .wrap {
                width: 85%;
            }

            .login_form {
                width: 100%;
                background: none;
            }

            .login_form li {
                width: 99.5%;
            }

            .login_form input[type="submit"] {
                width: 100%;
                margin: 22px 0 13px;
            }

            .account {
                width: 100%;
            }

            .account h2 a {
                text-align: left;
                margin-top: 15px;
            }

            .footer p {
                font-size: 1em;
            }
        }

    </style>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</head>
<body>
<div class="wrap">
    <div class="login-form">
        <c:url value='/j_spring_security_check' var="loginUrl"/>
        <form class="login_form" name='loginForm' action="${loginUrl}" method='POST'>
            <h1>Login Into Your Account</h1>
            <ul>
                <li>
                    <input type="text" class="textbox1" id="username" name="j_username" placeholder="Username" required="required">
                    <p><img src="../resources/images/contact.png" alt=""></p>
                </li>
                <li>
                    <input type="password" id="password" name="j_password" class="textbox2" placeholder="Password">
                    <p><img src="../resources/images/lock.png" alt=""></p>
                </li>
            </ul>
            <input type="submit" name="Sign In" value="Sign In">
        </form>
        <c:url value="../registration" var="signupUrl"/>
        <div class="account">
            <h2><a href="${signupUrl}">Don't have an account? Sign Up!</a></h2>
        </div>
        <div class="clear"></div>
    </div>
</div>
</body>
</html>
