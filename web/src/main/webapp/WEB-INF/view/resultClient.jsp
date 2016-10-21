<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<title>User Page</title>
<link rel="stylesheet" href="css/normalize.css">
<link rel="stylesheet" href="css/styles.css">
</head>
<body>
	<h1><font color="#b0cd2e">${pageClient}</font></h1>
	<a href="ServletRestoraut?command=logout">"${logout}"</a>
	<table class="rwd-table">
		<tr>
			<th>${name}</th>
			<th>${tableNumber}</th>
			<th>${timeArrival}</th>
			<th>${chekOrder}</th>
		</tr>
		<tr>
			<td data-th="Movie Title">${user}</td>
			<td data-th="Genre">${table}</td>
			<td align="center" data-th="Year">${time}</td>
			<td data-th="Gross">${sum}${cost}<br><a href="ServletRestoraut?command=countClient">${countCLient}</a></td>
		</tr>
	</table>
	<table class="rwd-table">
		<tr>
			<th>${menuNameList}</th>
		</tr>
		<tr>
			<td data-th="Movie Title">
				<form action="ServletRestoraut" method="post">
					<select size="3" name="menuForOrder">
						<c:forEach var="menu" items="${menus}">
							<option value="${menu}">№ ${menu}${cost} ${menu.weight} ${unit}</option>
						</c:forEach>
					</select>
					<p>
						<input placeholder="${commentaries}" name="comment">
					</p>
					<p>
						<input type="hidden" name="command" value="Order">
						<input type="submit" value="${orderCommand}">
					</p>
					${errorChooseCheked}				
				</form>
				
			</td>
		</tr>
	</table>
		
	<jsp:include page="order.jsp" />	
</body>
</html>