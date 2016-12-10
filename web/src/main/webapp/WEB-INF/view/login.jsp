<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>Login</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>

	<form action=http://localhost:8080/projectRestaurant/login name="loginForm" class="login">
		<input type="hidden" name="command" value="login" class="login-input" />		${loginQuest}
		<input name="login" type="text" value="" class="login-input" /><br> ${passwordQuest}
		<input name="password" type="password" value="" class="login-input" /><br>${errorLoginPasswordMessage} <br>
		<input type="submit" value="${logIn}" />
	</form>

	<form action=http://localhost:8080/projectRestaurant/language name="language" class="login" >
			<p align="center"><input type="hidden" name="command" value="language"/>
            <input name="language" type="submit" value="EN">
            <input name="language" type="submit" value="RU" ></p>
	</form>
</body>
</html>