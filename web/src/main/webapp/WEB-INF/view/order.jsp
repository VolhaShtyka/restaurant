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
			<th>${orderClient}</th>
		</tr>
		<tr>
			<td data-th="Movie Title">
				<form action="ServletRestoraut" method="post">
					<c:forEach var="order" items="${orders}">
					${nameMeal}			
					<p>${orderNumber}${order}</p>
					</c:forEach>
					
					<input type="hidden" name="command" value="Cooking">
					<input type="submit" value="${cookingCommand}">
					</form>
					<form action="ServletRestoraut" method="post">
					<input type="hidden" name="command" value="Clear">
					<input	type="submit" value="${clearCommand}"><br>
					${errorCookingMessage}
				</form>
			</td>
		</tr>
	</table>


</body>
</html>