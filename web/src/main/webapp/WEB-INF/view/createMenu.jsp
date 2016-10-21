<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<title>User Page</title>
<link rel="stylesheet" href="css/normalize.css">
<link rel="stylesheet" href="css/styles.css">
</head>
<body>
		<table class="rwd-table">
		<tr>
			<th>${menuNameList}</th>
		</tr>
		<tr>
			<td data-th="Movie Title">
				<form action="ServletRestoraut" method="post">					
					<input placeholder="${mealNameRU}" name="mealNameNewRU"><br>
					<input placeholder="${mealNameEN}" name="mealNameNewEN"><br>					
					<input placeholder="${price}" name="priceNew"><br>
					<input placeholder="${weight}" name="weightNew"><br>
					<input type="hidden" name="command" value="newMenu"><br>
					<input type="submit" value="${newMenu}"><br>					
				</form>
			</td>
		</tr>
	</table>
</body>
</html>