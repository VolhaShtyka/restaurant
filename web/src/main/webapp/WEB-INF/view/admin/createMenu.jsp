<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<html>
<head>
<title>User Page</title>
	<link rel="stylesheet" href="<c:url value="/css/styles.css"/>">
	<link rel="stylesheet" href="<c:url value="/css/normalize.css"/>">
</head>
<body>
		<table class="rwd-table">
		<tr>
			<th>${menuNameList}</th>
		</tr>
		<tr>
			<td data-th="Movie Title">
				<form action=http://localhost:8080/projectRestaurant/admins/newMenu>
					<input placeholder=<s:message code="mealNameRU"/> name="mealNameNewRU"><br>
					<input placeholder=<s:message code="mealNameEN"/> name="mealNameNewEN"><br>
					<input placeholder=<s:message code="price"/> name="priceNew"><br>
					<input placeholder=<s:message code="weight"/> name="weightNew"><br>
					<input type="submit" value=<s:message code="newMenu"/>><br>
				</form>
			</td>
		</tr>
	</table>
</body>
</html>