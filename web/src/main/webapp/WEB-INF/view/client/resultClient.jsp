<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<html>
<head>
<title>User Page</title>
    <link rel="stylesheet" href="<c:url value="/css/styles.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/normalize.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/stylePagination.css"/>">
</head>
<body>
	<h1><span style="color: #b0cd2e; "><s:message code="pageClient"/></span></h1>
	<a href="http://localhost:8080/projectRestaurant/logout"><s:message code="logout"/></a>
	<table class="rwd-table">
		<tr>
			<th><s:message code="name"/></th>
			<th><s:message code="tableNumber"/></th>
			<th><s:message code="timeArrival"/></th>
			<th><s:message code="chekOrder"/></th>
		</tr>
		<tr>
			<td data-th="Movie Title">${user}</td>
			<td data-th="Genre">${table}</td>
			<td align="center" data-th="Year">${time}</td>
			<td data-th="Gross">${sum}${cost}<br><a href="http://localhost:8080/projectRestaurant/clients/countClient"><s:message code="countCLient"/></a></td>
		</tr>
	</table>


	<table class="rwd-table">
		<tr>
			<th><s:message code="menuNameList"/></th>

		</tr>

			<td data-th="Movie Title">
				<form action=http://localhost:8080/projectRestaurant/clients/order >
					<select multiple name="menuForOrder">
						<c:forEach var="menu" items="${menus}">
							<option value="${menu}">№ ${menu}<s:message code="cost"/> ${menu.weight} <s:message code="unit"/></option>
						</c:forEach>
					</select>
					<p>
						<input placeholder=<s:message code="commentaries"/> name="comment">
					</p>
					<p>
						<input type="submit" value=<s:message code="orderCommand"/>>
					</p>
					${errorChooseCheked}
				</form>
			</td>

        <th>
            <form action=http://localhost:8080/projectRestaurant/clients/sorting>
                <input name="sortPriceOrWeight" type="submit" value="1"/> ${priceASC}<br>
                <input name="sortPriceOrWeight" type="submit" value="2"/> ${priceDESC}<br>
                <input name="sortPriceOrWeight" type="submit" value="3"/> ${weightASC}<br>
                <input name="sortPriceOrWeight" type="submit" value="4"/> ${weightDESC}<br>
            </form>
        </th>


            <td>
            <form action=http://localhost:8080/projectRestaurant/clients/sort>
                <div style="width: 150%; margin-left: 3%"><s:message code="choosePrice"/>
                <input name="minPrice" type="text" value=""/>
                <input name="maxPrice" type="text" value=""/><br></div>
                <s:message code="chooseWeight"/>
                <input name="minWeight" type="text" value=""/>
                <input name="maxWeight" type="text" value=""/><br>
                <input type="submit" value=<s:message code="show"/> />
            </form>
            <form action=http://localhost:8080/projectRestaurant/clients/clearSort>
                <input type="submit" value=<s:message code="reset"/> />
            </form>
            </td>
		</td>
	</table>

		<table border="1" cellpadding="5" cellspacing="5" bgcolor="#f0f8ff">
			<tr>
				<c:if test="${currentPage != 1}">
					<td>
						<a href="http://localhost:8080/projectRestaurant/clients/client?currentPage=${currentPage - 1}">Предыдущая
						</a>
					</td>
				</c:if>
				<c:forEach begin="1" end="${numberOfPages}" var="i">
					<c:choose>
						<c:when test="${currentPage eq i}">
							<td>${i}</td>
						</c:when>
						<c:otherwise>
							<td>
								><a href="http://localhost:8080/projectRestaurant/clients/client?currentPage=${i}">${i}</a>
							</td>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:if test="${currentPage lt numberOfPages}">
					<td>
						<a href="http://localhost:8080/projectRestaurant/clients/client?currentPage=${currentPage + 1}">Следующая</a>
					</td>
				</c:if>
			</tr>
		</table>
	<jsp:include page="order.jsp" />
</body>
</html>