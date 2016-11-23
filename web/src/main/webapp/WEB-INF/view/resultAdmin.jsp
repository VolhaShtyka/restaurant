<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<title>User Page</title>
<link rel="stylesheet" href="css/normalize.css">
<link rel="stylesheet" href="css/styles.css">
</head>
<body link="red" vlink="#b0cd2e" alink="#b0cd2e">
	<h1><span style="color: #b0cd2e; ">${pageAdmin}</span></h1>
	<table class="rwd-table" >
		<tr>
			<th>${infoUser}</th>
		</tr>
		<tr>
			<c:forEach var="user" items="${users}">
				<td data-th="Movie Title" >${name}: ${user.name}. ${tableNumber}: ${user.tableNumber} <a href="ServletRestaurant?command=delete">${delete}</a></td><br>
			</c:forEach>			
		</tr>
	</table>
	<table class="rwd-table">
		<tr>
			<th>${chekInfo}</th>
		</tr>
		<tr>
			<td data-th="Movie Title">
				<form action="ServletRestaurant" method="post">
					<c:forEach var="order" items="${orders}">
						<option value="${order}">${orderNumber}${order}</option><br>
					</c:forEach>
					<p>${sum} ${cost}</p>
					${commentaries} ${comment}
					<p>
						<input type="hidden" name="command" value="Ready">
						<input type="submit" value="${readyOrder}">
					</p>
					${errorCookingCheked}
				</form>
			</td>
		</tr>
	</table>
	<a href="ServletRestaurant?command=newMenuCreat">${newMenu}</a>
	<table class="rwd-table">	
		<tr>
			<th>${actionWithOrder}</th>			
		</tr>
		<tr>
			<td data-th="Movie Title">				
				<form action="ServletRestaurant" method="post">
					<input type="hidden" name="command" value="Discount">
					<input type="submit" value="${discount}">
				</form>
				<form action="ServletRestaurant" method="post">
					<input type="hidden" name="command" value="Allowance"> 
					<input type="submit" value="${allowance}">
				</form>
				<form action="ServletRestaurant" method="post">
					<input type="hidden" name="command" value="Count">
					<input type="submit" value="${count}">
					${errorCookingCheked}
				</form>
			</td>
		</tr>
	</table>
<table class="rwd-table">
	<tr>
	<td>
	<a href="ServletRestaurant?command=more">${more}</a>
	<a href="ServletRestaurant?command=logout">${logout}</a>
	</td>
	</tr>
</table>

</body>
</html>