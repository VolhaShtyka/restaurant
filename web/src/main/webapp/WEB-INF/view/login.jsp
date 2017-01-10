<%@page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<%--<form action=http://localhost:8080/projectRestaurant/login class="login" method="post">--%>
<%--<s:message code="loginQuest"/>--%>
<%--<input name="login" type="text" value="" class="login-input" /> <br>--%>
<%--<s:message code="passwordQuest"/>--%>
<%--<input name="password" type="password" value="" class="login-input" /><br>--%>
<%--${errorLoginPasswordMessage} <br>--%>
<%--<input type="submit"  value="<s:message code="logIn"/>"/>--%>
<%--</form>--%>

<form method="post" class="login" action="<c:url value='/j_spring_security_check'/>">
    <fieldset>
        <s:message code="loginQuest"/>
        <input class="login-input" id="login" name="login"
               type="text"/><br>

        <s:message code="passwordQuest"/>
        <input class="login-input" id="password" name="password"
               type="password"/><br>
        <input type="submit" value="<s:message code="logIn"/>"/>

    </fieldset>
</form>

<table class="login">
    <p align="center">
        <a href="?locale=ru">RU</a>
        <a href="?locale=en">EN</a>
    </p>
</table>
</body>
</html>