<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<title>User Page</title>
<link rel="stylesheet" href="css/normalize.css">
<link rel="stylesheet" href="css/styles.css">
<link rel="stylesheet" href="css/stylePagination.css">
</head>
<body>
	<h1><span style="color: #b0cd2e; ">${pageClient}</span></h1>
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
					<select multiple name="menuForOrder">
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

    <c:choose>
        <c:when test="${currentPage != 1}">
            <td><a href="ServletRestoraut?command=client&currentPage=${currentPage - 1}">Предыдущая</a></td>
        </c:when>
        <c:otherwise>
            <td></td>
        </c:otherwise>
    </c:choose>

    <c:forEach begin="1" end="${numberOfPages}" var="i">
        <c:choose>
            <c:when test="${currentPage eq i}">
                <td>${i}</td>
            </c:when>
            <c:otherwise>
                <td><a href="ServletRestoraut?command=client&currentPage=${i}">${i}</a></td>
            </c:otherwise>
        </c:choose>
    </c:forEach>

    <c:choose>
        <c:when test="${currentPage lt numberOfPages}">
            <td><a href="ServletRestoraut?command=client&currentPage=${currentPage + 1}">Следующая</a></td>
        </c:when>
        <c:otherwise>
            <td></td>
        </c:otherwise>
    </c:choose>
	<jsp:include page="order.jsp" />	
</body>
</html>